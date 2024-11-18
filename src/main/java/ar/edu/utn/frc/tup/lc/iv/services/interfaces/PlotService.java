package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotWithHisOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.FilePlotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotStateEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotTypeEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * Obtiene un lote por id de Owner.
     *
     * @param ownerdId id del propietario para buscar el lote.
     * @return los lotes encontrados.
     */
    List<GetPlotDto> getPlotByOwnerId(Integer ownerdId);

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

    /**
     * Obtiene todos los lotes con sus propietarios.
     *
     * @return una lista con todos los lotes y sus propietarios.
     */
    List<GetPlotWithHisOwnerDto> getPlotsWithHisOwner();

    /**
     * Valida si el lote es de tipo "wasteland" y no tiene metros construidos.
     *
     * @param postPlotDto Datos del lote a validar.
     */
    void validateWasteland(PostPlotDto postPlotDto);

    /**
     * Valida si el área construida es menor que el área total del lote.
     *
     * @param postPlotDto Datos del lote a validar.
     */
    void validateBuiltArea(PostPlotDto postPlotDto);

    /**
     * Sube archivos relacionados a un lote.
     *
     * @param files    Los archivos a subir.
     * @param userId   El id del usuario que sube los archivos.
     * @param plotEntity El lote al que se asociarán los archivos.
     */
    void uploadFiles(List<MultipartFile> files, Integer userId, PlotEntity plotEntity);

    /**
     * Crea una entidad de archivo de lote.
     *
     * @param file       El archivo a asociar al lote.
     * @param userId     El id del usuario que sube el archivo.
     * @param plotEntity El lote al que se asociará el archivo.
     * @return La entidad de archivo creada.
     */
    FilePlotEntity createFilePlotEntity(MultipartFile file, Integer userId, PlotEntity plotEntity);

    /**
     * Encuentra un lote por su id.
     *
     * @param plotId El id del lote a buscar.
     * @return La entidad de lote encontrada.
     */
    PlotEntity getPlotEntityById(Integer plotId);

    /**
     * Actualiza los campos de un lote.
     *
     * @param plotEntity la entidad de lote a actualizar.
     * @param plotDto los nuevos datos del lote.
     */
    void updatePlotFields(PlotEntity plotEntity, PutPlotDto plotDto);

    /**
     * Mapea los datos de una entidad de lote a un DTO con los detalles del propietario.
     *
     * @param plotEntity          La entidad de lote a mapear.
     * @param getPlotDto          El DTO de lote donde se mapearán los datos.
     */
    void mapPlotEntityToGetPlotWithHisOwnerDto(PlotEntity plotEntity, GetPlotWithHisOwnerDto getPlotDto);

    /**
     * Valida si un lote existe con el número de lote proporcionado.
     *
     * @param plotNumber El número del lote a validar.
     */
    void validatePlotNumber(Integer plotNumber);

    /**
     * Mapea los datos de un DTO post de lote a una entidad de lote.
     *
     * @param postPlotDto El DTO de lote con los datos para mapear.
     * @return La entidad de lote creada a partir del DTO.
     */
     PlotEntity mapPlotPostToPlotEntity(PostPlotDto postPlotDto);

    /**
     * Mapea los datos de un DTO post de lote a una entidad de lote existente.
     *
     * @param plotEntity  La entidad de lote existente que será actualizada.
     * @param postPlotDto El DTO con los nuevos datos para actualizar la entidad.
     */
    void mapPlotPostToPlotEntity(PlotEntity plotEntity, PostPlotDto postPlotDto);

    /**
     * Obtiene un estado de lote por su id.
     *
     * @param id El id del estado del lote a obtener.
     * @return El estado de lote correspondiente al id.
     */
    PlotStateEntity getPlotState(Integer id);

    /**
     * Obtiene un tipo de lote por su id.
     *
     * @param id El id del tipo de lote a obtener.
     * @return El tipo de lote correspondiente al id.
     */
    PlotTypeEntity getPlotType(Integer id);
}
