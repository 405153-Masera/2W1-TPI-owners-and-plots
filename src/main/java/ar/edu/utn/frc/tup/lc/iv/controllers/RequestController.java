package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.RequestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar la lógica de solicitudes de compra de lotes.
 */
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    /**
     * Servicio para manejar la lógica de solicitudes de compra de lotes.
     */
    private final RequestService requestService;

    /**
     * Guarda una nueva solicitud.
     *
     * @param requestDTO La solicitud a guardar.
     * @return Un ResponseEntity con la respuesta del servicio.
     */
    @PostMapping()
    public ResponseEntity<GetRequestDTO> postRequest(@Valid @RequestBody GetRequestDTO requestDTO) {
        GetRequestDTO requestDTOsaved = requestService.createRequest(requestDTO);
        return ResponseEntity.ok(requestDTOsaved);
    }

    /**
     * Obtiene una solicitud por id.
     *
     * @param id id de la solicitud a obtener.
     * @return la solicitud.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetRequestDTO> getRequestById(@PathVariable Integer id) {
        GetRequestDTO result = requestService.getRequestById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Actualiza el estado de una solicitud existente en la base de datos.
     *
     * @param id id de la solicitud a modificar.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequestState(@PathVariable Integer id) {
        requestService.changeRequestState(id);
        return ResponseEntity.ok().build();
    }
}
