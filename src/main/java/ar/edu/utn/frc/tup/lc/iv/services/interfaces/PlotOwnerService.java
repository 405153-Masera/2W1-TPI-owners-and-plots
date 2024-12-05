package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlotOwnerService {

    /**
     * Devuelve una lista de GetPlotOwnerDto que contiene los identificadores
     * de los propietarios y los lotes.
     *
     * @return una lista de GetPlotOwnerDto.
     */
    List<GetPlotOwnerDto> getAllPlotOwner();

    /**
     * Borra la relacion entre un propietario y un lote.
     *
     * @param ownerId el identificador del propietario.
     * @param plotId el identificador del lote.
     */
    void deletePlotOwner(Integer ownerId, Integer plotId);

    /**
     * Crea la relacion entre un propietario y un lote.
     *
     * @param ownerId el identificador del propietario.
     * @param plotId el identificador del lote.
     */
    void createPlotOwner(Integer ownerId, Integer plotId, Integer userId);

    /**
     * Mapea la entidad PlotOwnerEntity.
     */
     void mapPlotOwnerEntity(Integer ownerId, Integer plotId, Integer userId);
}
