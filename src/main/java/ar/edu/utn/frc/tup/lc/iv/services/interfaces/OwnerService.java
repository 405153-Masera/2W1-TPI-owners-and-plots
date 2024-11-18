package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Interfaz que contiene la lógica de propietarios.
 */
@Service
public interface OwnerService {

    /**
     * Crea un propietario y el usuario del propietario.
     *
     * @param postOwnerDto el DTO con la información del propietario y su usuario.
     * @return el DTO con la información del propietario creado.
     */
    GetOwnerDto createOwner(PostOwnerDto postOwnerDto);

    /**
     * Actualiza un propietario.
     *
     * @param ownerId el id del propietario a actualizar.
     * @param putOwnerDto el DTO con la información del propietario a actualizar.
     * @return el DTO con la información del propietario actualizado.
     */
    GetOwnerDto updateOwner(Integer ownerId, PutOwnerDto putOwnerDto);

    /**
     * Obtiene un propietario por id.
     *
     * @param ownerId el id del propietario a buscar.
     * @return el DTO con la información del propietario.
     */
    GetOwnerDto getById(Integer ownerId);

    /**
     * Obtiene los tipos de situación fiscal.
     *
     * @return una lista de los tipos de situación fiscal.
     */
    List<GetTaxStatusDto> getTaxStatus();

    /**
     * Obtiene los tipos de documento.
     *
     * @return una lista de los tipos de documento.
     */
    List<GetDniTypeDto> getDniTypes();

    /**
     * Obtiene todos los tipos de propietarios (personas física, jurídica, otros).
     *
     * @return una lista con los tipos de propietarios.
     */
    List<GetOwnerTypeDto> getOwnerTypes();

    /**
     * Obtiene todos los propietarios.
     *
     * @return una lista con todos los propietarios.
     */
    List<GetOwnerDto> getAllOwners();

    /**
     * Obtiene todos los propietarios activos, junto con su lote y su usuario.
     *
     * @return una lista con todos los propietarios activos, su lote y su usuario.
     */
    List<GetOwnerAndPlot> getOwersAndPlots();

    /**
     * Obtiene los propietarios de un lote.
     *
     * @param plotId el id del lote a buscar.
     * @return una lista con los propietarios del lote.
     */
    List<OwnerDto> getOwnersByPlotId(Integer plotId);

    /**
     * Baja lógica de un propietario y su usuario.
     *
     * @param ownerId el id del propietario a dar de baja.
     * @param userIdUpdate el id del usuario que da de baja al propietario.
     */
    void deleteOwner(Integer ownerId, Integer userIdUpdate);

    /**
     * Obtiene un propietario por su id, junto con su lote y su usuario.
     *
     * @param ownerId el id del propietario a buscar.
     * @return el propietario, su lote y su usuario.
     */
    GetOwnerAndPlot getOwnerAndPlotById(Integer ownerId);

    /**
     * Obtiene todos los propietarios con sus lotes.
     *
     * @return una lista con todos los propietarios y sus lotes.
     */
    List<GetOwnerWithHisPlots> getallOwnersWithTheirPlots();

    /**
     * Obtiene la cantidad de propietarios activos e inactivos.
     *
     * @return un mapa con la cantidad de propietarios activos e inactivos
     */

    Map<String, Long> getOwnerCountByStatus();

}
