package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    /**
     * Servicio para manejar la lógica de propietarios.
     */
    private final OwnerService ownerService;

    /**
     * Constructor de OwnerController.
     *
     * @param ownerService servicio de propietarios.
     */
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Guarda un nuevo propietario.
     *
     * @param postOwnerDto datos del propietario a guardar.
     * @return el propietario creado.
     */
    @PostMapping
    public ResponseEntity<GetOwnerDto> postOwner(@RequestBody PostOwnerDto postOwnerDto) {
        GetOwnerDto getOwnerDto = ownerService.createOwner(postOwnerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(getOwnerDto);
    }

    /**
     * Actualiza un propietario.
     *
     * @param ownerId id del propietario a actualizar.
     * @param putOwnerDto datos del propietario a actualizar.
     * @return el propietario actualizado.
     */
    @PutMapping("/{ownerId}")
    public ResponseEntity<GetOwnerDto> putOwner(@PathVariable Integer ownerId, @RequestBody PutOwnerDto putOwnerDto) {
        GetOwnerDto getOwnerDto = ownerService.updateOwner(ownerId, putOwnerDto);
        return ResponseEntity.ok(getOwnerDto);
    }

    /**
     * Obtiene un propietario por id.
     *
     * @param ownerId id del propietario a obtener.
     * @return el propietario.
     */
    @GetMapping("/{ownerId}")
    public ResponseEntity<GetOwnerDto> getOwnerById(@PathVariable Integer ownerId) {
        GetOwnerDto result = ownerService.getById(ownerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene los tipos de situación fiscal.
     *
     * @return lista de los tipos de situación fiscal.
     */
    @GetMapping("/taxstatus")
    public ResponseEntity<List<GetTaxStatusDto>> getTaxStatus() {
        return ResponseEntity.ok(ownerService.getTaxStatus());
    }

    /**
     * Obtiene los tipos de propietarios.
     *
     * @return lista de los tipos de propietarios.
     */
    @GetMapping("/ownertypes")
    public ResponseEntity<List<GetOwnerTypeDto>> getOwnerTypes() {
        return ResponseEntity.ok(ownerService.getOwnerTypes());
    }

    /**
     * Obtiene todos los propietarios.
     *
     * @return una lista con todos los propietarios.
     */
    @GetMapping()
    public ResponseEntity<List<GetOwnerDto>> getOwners() {
        List<GetOwnerDto> result = ownerService.getAllOwners();

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene los propietarios de un lote.
     *
     * @param plotId el id del lote a buscar.
     * @return una lista con los propietarios del lote.
     */
    @GetMapping("/plot/{plotId}")
    public ResponseEntity<List<OwnerDto>> getOwnersByPlotId(@PathVariable Integer plotId) {
        List<OwnerDto> owners = ownerService.getOwnersByPlotId(plotId);
        return ResponseEntity.ok(owners);
    }

    /**
     * Obtiene todos los propietarios activos, junto con su lote y su usuario.
     *
     * @return una lista con todos los propietarios activos, su lote y su usuario.
     */
    @GetMapping("/ownersandplots")
    public ResponseEntity<List<GetOwnerAndPlot>> getOwnersPlots() {
        return ResponseEntity.ok(ownerService.getOwersAndPlots());
    }

    /**
     * Obtiene un propietario por su id, junto con su lote y su usuario.
     *
     * @param ownerId el id del propietario a buscar.
     * @return el propietario, su lote y su usuario.
     */
    @GetMapping("/ownersandplots/{ownerId}")
    public ResponseEntity<GetOwnerAndPlot> getOwnerAndPlotById(@PathVariable Integer ownerId) {
        GetOwnerAndPlot ownerAndPlot = ownerService.getOwnerAndPlotById(ownerId);
        if (ownerAndPlot == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerAndPlot);
    }

    /**
     * Baja lógica de un propietario.
     *
     * @param ownerId id del propietario a dar de baja.
     * @param userIdUpdate id del usuario realiza la baja.
     * @return respuesta vacía.
     */
    @DeleteMapping("/{id}/{userIdUpdate}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Integer ownerId, @PathVariable("userIdUpdate") Integer userIdUpdate) {
        ownerService.deleteOwner(ownerId, userIdUpdate);
        return ResponseEntity.noContent().build();
    }
}
