package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.RequestEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaz que contiene la lógica de las solicitudes de compra de lotes
 */
@Service
public interface RequestService {

    /**
     * Obtiene todas las solicitudes de compra de lotes
     *
     * @return una lista con todas las solicitudes de compra de lotes
     */
    List<GetRequestDTO> getAllRequests();

    /**
     * Mapea los datos de una entidad solicitud a una solicitud
     *
     * @param requestEntity datos de la  solicitud a mapear.
     * @return solicitud mapeada.
     */
    GetRequestDTO mapRequestEntityToDTO(RequestEntity requestEntity);

    /**
     * Mapea los datos de un solicitud a una entidad de solicitud.
     *
     * @param requestDTO datos de la  solicitud a mapear.
     * @return entidad de solicitud mapeada.
     */
    RequestEntity mapRequestDTOToEntity(GetRequestDTO requestDTO);

    /**
     * Obtiene una solicitud por su id.
     *
     * @param requestId el id de la solicitud a buscar.
     * @throws EntityNotFoundException si no se encuentra la solicitud.
     * @return el DTO con la información de la solicitud.
     */
    GetRequestDTO getRequestById(Integer requestId);

    /**
     * Cambia el estado de una solicitud a contactado.
     *
     * @param requestId id de la solicitud a modificar
     */
    void changeRequestState(Integer requestId);

    /**
     * Crea una nueva solicitud
     *
     * @param requestDTO datos de la solicitud a guardar.
     * @return solicitud creada.
     */
    GetRequestDTO createRequest(GetRequestDTO requestDTO);
}
