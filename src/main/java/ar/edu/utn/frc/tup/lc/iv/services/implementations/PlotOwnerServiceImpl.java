package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class PlotOwnerServiceImpl implements PlotOwnerService {

    /**
     * Repositorio para manejar la entidad PlotOwnerEntity.
     */
    private final PlotOwnerRepository plotOwnerRepository;

    /**
     * Repositorio para manejar la entidad OwnerEntity.
     */
    private final OwnerRepository ownerRepository;

    /**
     * Repositorio para manejar la entidad PlotEntity.
     */
    private final PlotRepository plotRepository;

    /**
     * Devuelve una lista de GetPlotOwnerDto que contiene los identificadores
     * de los propietarios y los lotes.
     *
     * @return una lista de GetPlotOwnerDto.
     */
    @Override
    public List<GetPlotOwnerDto> getAllPlotOwner() {
        List<PlotOwnerEntity> plotOwnerEntities = plotOwnerRepository.findAll();
        System.out.println("Entidades encontradas: " + plotOwnerEntities.size());

        return plotOwnerEntities.stream()
                .map(POE -> {
                    System.out.println("Mapeando entidad: " + POE.getId());
                    GetPlotOwnerDto plotOwnerDto = new GetPlotOwnerDto();
                    plotOwnerDto.setPlot_id(POE.getPlot().getId());
                    plotOwnerDto.setOwner_id(POE.getOwner().getId());
                    return plotOwnerDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Borra la relacion entre un propietario y un lote.
     *
     * @param ownerId el identificador del propietario.
     * @param plotId el identificador del lote.
     */
    @Override
    public void deletePlotOwner(Integer ownerId, Integer plotId) {
        plotOwnerRepository.deleteByOwnerIdAndPlotId(ownerId, plotId);
    }

    /**
     * Crea la relacion entre un propietario y un lote.
     *
     * @param ownerId el identificador del propietario.
     * @param plotId el identificador del lote.
     * @param userId el usuario que crea el mapeo.
     */
    @Override
    public void createPlotOwner(Integer ownerId, Integer plotId, Integer userId) {
        mapPlotOwnerEntity(ownerId, plotId, userId);
    }

    /**
     * Mapea la entidad PlotOwnerEntity.
     *
     * @param ownerId el identificador del propietario.
     * @param plotId el identificador del lote.
     * @param userId el identificador del usuario que crea el mapeo.
     */

    @Override
    public void mapPlotOwnerEntity(Integer ownerId, Integer plotId, Integer userId) {
        PlotOwnerEntity plotOwnerEntity = new PlotOwnerEntity();
        plotOwnerEntity.setOwner(ownerRepository.findById(ownerId).get());
        plotOwnerEntity.setPlot(plotRepository.findById(plotId).get());
        plotOwnerEntity.setCreatedDatetime(LocalDateTime.now());
        plotOwnerEntity.setCreatedUser(userId);
        plotOwnerEntity.setLastUpdatedUser(userId);
        plotOwnerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotOwnerRepository.save(plotOwnerEntity);
    }

}
