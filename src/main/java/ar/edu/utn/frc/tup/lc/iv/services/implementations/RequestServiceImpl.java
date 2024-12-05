package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.RequestEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.RequestRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.RequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    /**
     * Repositorio para manejar Request entities.
     */
    private final RequestRepository requestRepository;
    /**
     * Servicio para manejar los plots.
     */
    private final PlotService plotService;

    /**
     * Obtiene todos las solicitudes
     *
     * @return una lista con todos las solicitudes.
     */
    @Override
    public List<GetRequestDTO> getAllRequests() {
        List<RequestEntity> requestEntities = requestRepository.findAll();
        List<GetRequestDTO> requestDTOS = new ArrayList<>();
        List<GetPlotDto> plotDtos = plotService.getAllPlots();
        for (RequestEntity requestEntity : requestEntities) {
            GetPlotDto plotDto = null;
            if(requestEntity.getId() != null){
                plotDto=plotDtos.stream().filter(m->requestEntity.getId().equals(m.getId())).findFirst().orElse(null);
            }
            GetRequestDTO requestDTO = mapRequestEntityToDTO(requestEntity);
            if(Objects.nonNull(plotDto)){
                requestDTO.setBlockNumber(plotDto.getBlock_number());
                requestDTO.setPlotNumber(plotDto.getPlot_number());
            }
            requestDTOS.add(requestDTO);

        }
        return requestDTOS;
    }

    /**
     * Mapea los datos de una entidad solicitud a una solicitud
     *
     * @param requestEntity datos de la  solicitud a mapear.
     * @return solicitud mapeada.
     */
    @Override
    public GetRequestDTO mapRequestEntityToDTO(RequestEntity requestEntity) {
        GetRequestDTO requestDTO = new GetRequestDTO();
        requestDTO.setId(requestEntity.getId());
        requestDTO.setName(requestEntity.getName());
        requestDTO.setEmail(requestEntity.getEmail());
        requestDTO.setPhone(requestEntity.getPhone());
        requestDTO.setContacted(requestEntity.getContacted());
        requestDTO.setLotId(requestEntity.getLotId());
        requestDTO.setObservations(requestEntity.getObservations());
        requestDTO.setRequestDate(LocalDateTime.now());
        return requestDTO;
    }

    /**
     * Mapea los datos de un solicitud a una entidad de solicitud.
     *
     * @param requestDTO datos de la  solicitud a mapear.
     * @return entidad de solicitud mapeada.
     */
    @Override
    public RequestEntity mapRequestDTOToEntity(GetRequestDTO requestDTO) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setId(requestDTO.getId());
        requestEntity.setName(requestDTO.getName());
        requestEntity.setEmail(requestDTO.getEmail());
        requestEntity.setPhone(requestDTO.getPhone());
        requestEntity.setContacted(requestDTO.getContacted());
        requestEntity.setLotId(requestDTO.getLotId());
        requestEntity.setObservations(requestDTO.getObservations());
        requestEntity.setRequestDate(LocalDateTime.now());
        return requestEntity;
    }

    /**
     * Obtiene una solicitud por su id.
     *
     * @param requestId el id de la solicitud a buscar.
     * @throws EntityNotFoundException si no se encuentra la solicitud.
     * @return el DTO con la información de la solicitud.
     */
    @Override
    public GetRequestDTO getRequestById(Integer requestId) {
        RequestEntity requestEntity = requestRepository.findById(requestId).orElseThrow(() ->
                new EntityNotFoundException("Request not found"));

        GetRequestDTO requestDTO = mapRequestEntityToDTO(requestEntity);
        return requestDTO;
    }

    /**
     * Cambia el estado de una solicitud a contactado.
     *
     * @param requestId id de la solicitud a modificar
     */
    @Override
    public void changeRequestState(Integer requestId) {
        RequestEntity requestEntity = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));

        requestEntity.setContacted(true);
        requestRepository.save(requestEntity);
    }

    /**
     * Crea una nueva solicitud
     *
     * @param requestDTO datos de la solicitud a guardar.
     * @return solicitud creada.
     */
    @Override
    public GetRequestDTO createRequest(GetRequestDTO requestDTO) {

        if(requestDTO.getEmail() == null && requestDTO.getPhone() == null){
            throw new EntityNotFoundException("El email y el telefono no pueden ser nulos");
        }
        RequestEntity requestEntity = mapRequestDTOToEntity(requestDTO);
        RequestEntity savedRequest = requestRepository.save(requestEntity);

        GetRequestDTO requestDTOSaved = mapRequestEntityToDTO(savedRequest);
        return requestDTOSaved;
    }



}