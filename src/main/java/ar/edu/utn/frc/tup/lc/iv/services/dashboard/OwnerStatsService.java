package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;

import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

import java.time.format.TextStyle;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerStatsService implements OwnerStatsInterface {

    /**
     * Repositorio para manejar los datos de los propietarios.
     */
    private final OwnerRepository ownerRepository;

    /**
     * Repositorio para manejar los datos de los lotes.
     */
    private final PlotRepository plotRepository;

    /**
     * Repositorio para manejar la relación entre propietarios y lotes.
     */
    private final PlotOwnerRepository plotOwnerRepository;

    /**
     * Obtiene una lista de datos de las manzanas.
     *
     * @return una lista de datos de las manzanas
     */
    @Override
    public List<BlockData> getBlocksData() {
        List<PlotEntity> plots = plotRepository.findAll();
        Map<Integer, BlockData> blockDataMap = plots.stream()
                .collect(Collectors.toMap(
                        PlotEntity::getBlockNumber,
                        plot -> new BlockData(plot.getBlockNumber(), plot.getTotalAreaInM2(), plot.getBuiltAreaInM2()),
                        (existing, newPlot) -> {
                            existing.setTotalArea(existing.getTotalArea() + newPlot.getTotalArea());
                            existing.setBuiltArea(existing.getBuiltArea() + newPlot.getBuiltArea());
                            return existing;
                        }
                ));
        return new ArrayList<>(blockDataMap.values());
    }

    /**
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes.
     *
     * @return un mapa donde la clave es el nombre del mes y el valor es otro mapa con el estado(activo/inactivo)
     * y el conteo de propietarios por ese estado
     */
    public Map<String, Map<String, Long>> getOwnerCountByStatusPerMonth() {
        List<OwnerEntity> owners = ownerRepository.findAll();
        Map<String, Map<String, Long>> ownersCountByStatusPerMonth = owners.stream()
                .collect(Collectors.groupingBy(
                        owner -> owner.getCreatedDatetime().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()),
                        Collectors.groupingBy(
                                owner -> owner.getActive() ? "Activos" : "Inactivos",
                                Collectors.counting()
                        )
                ));
        return ownersCountByStatusPerMonth;
    }

    /**
     * Obtiene el porcentaje de propietarios por estado fiscal.
     * @return un mapa donde la clave es el estado fiscal y el valor es el porcentaje de propietarios en ese estado
     */
    public Map<String, Double> getOwnerPercentageByTaxStatus() {
        List<OwnerEntity> owners = ownerRepository.findAll();
        long totalOwners = owners.size();
        Map<String, Long> countByTaxStatus = owners.stream()
                .collect(Collectors.groupingBy(
                        owner -> owner.getTaxStatus().getDescription(),
                        Collectors.counting()
                ));
        return countByTaxStatus.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / totalOwners
                ));
    }


    public List<PlotByPlotStateCountDTO> countPlotsByState() {
        List<Object[]> result = plotRepository.countPlotsByState();

        List<PlotByPlotStateCountDTO> dtoList = new ArrayList<>();

        for (Object[] row : result) {
            String stateName = (String) row[0];  // El primer valor del Object[] es el 'stateName'
            long count = (long) row[1];           // El segundo valor del Object[] es el 'count'
            dtoList.add(new PlotByPlotStateCountDTO(stateName, count));
        }
        return dtoList;
    }

    public List<PlotByPlotTypeCountDTO> countPlotsByType() {
        List<Object[]> result = plotRepository.countPlotsByType();
        List<PlotByPlotTypeCountDTO> dtoList = new ArrayList<>();
        for (Object[] row : result) {
            String typeName = (String) row[0];  // El primer valor es 'typeName'
            long count = ((Number) row[1]).longValue(); // Convertir a long de forma segura
            dtoList.add(new PlotByPlotTypeCountDTO(typeName, count));
        }
        return dtoList;
    }

    /**
     * Calcula las estadísticas generales de los lotes.
     *
     * @return un objeto con las estadísticas de los lotes.
     */
    public PlotsStats getStatsOfPlots() {
        List<PlotEntity> plots = plotRepository.findAll();

        long totalPlots = plots.size();
        long availablePlots = plots.stream().filter(plot -> plot.getPlotState().getId() == 1L).count();
        long constructionPlots = plots.stream().filter(plot -> plot.getPlotState().getId() == 3L).count();
        long occupiedPlots = plots.stream().filter(plot -> plot.getPlotState().getId() == 2L).count();
        double totalArea = plots.stream().mapToDouble(PlotEntity::getTotalAreaInM2).sum();
        double builtArea = plots.stream().mapToDouble(PlotEntity::getBuiltAreaInM2).sum();

        return new PlotsStats(totalPlots, availablePlots, constructionPlots, occupiedPlots, totalArea, builtArea);
    }

    /**
     * Agrupa los lotes por manzana y calcula sus estadísticas.
     *
     * @return una lista  con las estadísticas de los lotes agrupados por manzana.
     */
    public List<PlotsByBlock> getPlotsByBlock() {
        List<PlotEntity> plots = plotRepository.findAll();

        return plots.stream()
                .collect(Collectors.groupingBy(PlotEntity::getBlockNumber))
                .entrySet().stream()
                .map(plotsByBlock -> {
                    Integer blockNumber = plotsByBlock.getKey();
                    List<PlotEntity> blockPlots = plotsByBlock.getValue();
                    long availablePlots = blockPlots.stream().filter(plot -> plot.getPlotState().getId() == 2L).count();
                    long soldPlots = blockPlots.stream().filter(plot -> plot.getPlotState().getId() == 1L).count();
                    long inConstructionPlots = blockPlots.stream().filter(plot -> plot.getPlotState().getId() == 3L).count();
                    long totalPlots = blockPlots.size();
                    return new PlotsByBlock(blockNumber, availablePlots, soldPlots, inConstructionPlots, totalPlots);
                })
                .sorted(Comparator.comparing(PlotsByBlock::getBlockNumber))
                .collect(Collectors.toList());
    }

    public List<OwnersPlotsDistribution> getOwnersPlotsDistribution() {
        List<PlotOwnerEntity> plotOwners = plotOwnerRepository.findAll();

        return plotOwners.stream()
                .collect(Collectors.groupingBy(PlotOwnerEntity::getOwner))
                .entrySet().stream()
                .map(entry -> {
                    OwnerEntity owner = entry.getKey();

                    System.out.println(owner.getName());


                    List<PlotOwnerEntity> ownerPlots = entry.getValue();
                    System.out.println(ownerPlots);

                    String ownerName = owner.getName() == null ? "Sin Propietario" : (owner
                            .getOwnerType().getId() == 1L ? owner.getName() + " " + owner.getLastname() : owner.getBusinessName());

                    long countPlots = ownerPlots.size();
                    double totalArea = ownerPlots.stream()
                            .map(PlotOwnerEntity::getPlot)
                            .mapToDouble(PlotEntity::getTotalAreaInM2)
                            .sum();

                    return new OwnersPlotsDistribution(ownerName, countPlots, totalArea);
                })
                .sorted(Comparator.comparingLong(OwnersPlotsDistribution::getPlotCount).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    /*public List<PlotsStateDistribution> getPlotStateDistribution() {
        List<PlotEntity> plots = plotRepository.findAll();

        Map<Long, PlotsStateDistribution> stateDistributionMap = plots.stream()
                .collect(Collectors.groupingBy(plot -> plot.getPlotState().getId(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                statePlots -> {
                                    String stateName = statePlots.get(0).getPlotState().getName();
                                    String color = switch (statePlots.get(0).getPlotState().getId().intValue()) {
                                        case 1 -> "#4CAF50"; // Available
                                        case 2 -> "#2196F3"; // Sold
                                        case 3 -> "#FFC107"; // Under Construction
                                        default -> "#000000"; // Default color if needed
                                    };
                                    return new PlotsStateDistribution(stateName, statePlots.size(), color);
                                }
                        )
                ));

        return new ArrayList<>(stateDistributionMap.values());
    }*/

    public List<ConstructionProgress> getConstructionProgress() {
        List<PlotEntity> plots = plotRepository.findAll();

        return plots.stream()
                .filter(plot -> plot.getPlotState().getId() == 3L)
                .collect(Collectors.groupingBy(plot -> plot.getCreatedDatetime().getYear()))
                .entrySet().stream()
                .map(entry -> new ConstructionProgress(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparing(ConstructionProgress::getYear))
                .collect(Collectors.toList());
    }
}
