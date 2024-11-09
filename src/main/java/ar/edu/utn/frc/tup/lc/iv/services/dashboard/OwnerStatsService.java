package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.PlotByPlotStateCountDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;

import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;

import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import java.util.Map;
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
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes
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
     * Obtiene el porcentaje de propietarios por estado fiscal
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



}
