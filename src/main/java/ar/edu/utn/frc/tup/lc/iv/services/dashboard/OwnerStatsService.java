package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
