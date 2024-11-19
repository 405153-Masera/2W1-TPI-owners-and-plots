package ar.edu.utn.frc.tup.lc.iv.controllers;


import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;

import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plotOwners")
@RequiredArgsConstructor
public class PlotOwnerController {

    /**
     * Servicio para manejar la lógica de la relación entre propietarios y lotes.
     */
    private final PlotOwnerService plotOwnerService;

    /**
     * Servicio para manejar la lógica de los lotes.
     */
    private final PlotService plotService;

    /**
     * Obtiene la relación entre propietarios y lotes.
     *
     * @return lista de la relación entre propietarios y lotes.
     */
    @GetMapping()
    public ResponseEntity<List<GetPlotOwnerDto>> getUsers() {
        List<GetPlotOwnerDto> result = plotOwnerService.getAllPlotOwner();
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Transfiere la propiedad de un lote a otro propietario.
     *
     * @param plotId id del lote a transferir.
     * @param ownerId id del propietario actual.
     * @param userId id del nuevo propietario.
     * @return respuesta de la transferencia.
     */
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferPlotOwner(@RequestParam int plotId, @RequestParam int ownerId, @RequestParam int userId) {
        plotService.transferPlot(plotId, ownerId, userId);
        return ResponseEntity.ok().build();
    }
}
