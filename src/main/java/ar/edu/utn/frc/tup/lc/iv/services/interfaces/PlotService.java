package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaz que contiene la lógica de lotes.
 */
@Service
public interface PlotService {

    /**
     * Crea un nuevo lote.
     *
     * @param postPlotDto datos del lote a guardar.
     * @return el lote creado.
     */
    GetPlotDto createPlot(PostPlotDto postPlotDto);

    /**
     * Obtiene los estados de los lotes.
     *
     * @return lista de estados de los lotes.
     */
    List<GetPlotStateDto> getPlotStates();

    /**
     * Obtiene los tipos de los lotes.
     *
     * @return lista de tipos de los lotes.
     */
    List<GetPlotTypeDto> getPlotTypes();

    /**
     * Obtiene todos los lotes.
     *
     * @return una lista con todos los lotes.
     */
    List<GetPlotDto> getAllPlots();

    /**
     * Obtiene todos los lotes disponibles.
     *
     * @return una lista con todos los lotes disponibles.
     */
    List<GetPlotDto> getAllPlotsAvailables();

    /**
     * Obtiene un lote por su id.
     *
     * @param plotId id del lote a buscar.
     * @return el lote encontrado.
     */
    GetPlotDto getPlotById(Integer plotId);

    /**
     * Actualiza un lote.
     *
     * @param putPlotDto datos del lote a actualizar.
     * @param plotId  id del lote a actualizar.
     * @return el lote actualizado.
     */
    GetPlotDto putPlot(PutPlotDto putPlotDto, Integer plotId);

    /**
     * Mapea los datos de un lote a un dto de lote.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param getPlotDto dto de lote a mapear.
     */
    void mapPlotEntityToGetPlotDto(PlotEntity plotEntity, GetPlotDto getPlotDto);
}
