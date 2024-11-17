package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotWithHisOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con lotes (plots).
 */
@RestController
@RequestMapping("/plots")
public class PlotController {

    /**
     * Servicio para manejar la lógica de lotes.
     */
    private final PlotService plotService;

    /**
     * Constructor de PlotController.
     *
     * @param plotService servicio de lotes.
     */
    @Autowired
    public PlotController(PlotService plotService) {
        this.plotService = plotService;
    }

    /**
     * Obtiene todos los estados de los lotes.
     *
     * @return una lista con los estados de los lotes
     */
    @GetMapping("/states")
    public ResponseEntity<List<GetPlotStateDto>> getPlotStates() {
        return ResponseEntity.ok(plotService.getPlotStates());
    }

    /**
     * Obtiene todos los tipos de lotes.
     *
     * @return una lista con los tipos de lotes
     */
    @GetMapping("/types")
    public ResponseEntity<List<GetPlotTypeDto>> getPlotTypes() {
        return ResponseEntity.ok(plotService.getPlotTypes());
    }

     /**
     * Guarda un nuevo lote.
     *
     * @param plotDto datos del lote a guardar.
     * @return el lote creado.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetPlotDto> postPlot(@ModelAttribute PostPlotDto plotDto) {
        return ResponseEntity.ok(plotService.createPlot(plotDto));
    }

    /**
     * Actualiza un lote.
     *
     * @param plotDto datos del lote a actualizar.
     * @param plotId id del lote a actualizar.
     * @return el lote actualizado.
     */
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetPlotDto> postPlot(@ModelAttribute PutPlotDto plotDto, @RequestParam Integer plotId) {
        return ResponseEntity.ok(plotService.putPlot(plotDto, plotId));
    }

    /**
     * Obtiene todos los lotes.
     *
     * @return una lista con todos los lotes.
     */
    @GetMapping()
    public ResponseEntity<List<GetPlotDto>> getPlots() {
        return ResponseEntity.ok(plotService.getAllPlots());
    }

    /**
     * Obtiene todos los lotes con el ID de su owner.
     *
     * @return una lista con todos los lotes.
     */
    @GetMapping("/withHisOwner")
    public ResponseEntity<List<GetPlotWithHisOwnerDto>> getPlotsWithHisOwner() {
        return ResponseEntity.ok(plotService.getPlotsWithHisOwner());
    }

    /**
     * Obtiene todos los lotes disponibles.
     *
     * @return una lista con todos los lotes disponibles.
     */
    @GetMapping("/availables")
    public ResponseEntity<List<GetPlotDto>> getAllPlotsAvailables() {
        return ResponseEntity.ok(plotService.getAllPlotsAvailables());
    }

    /**
     * Obtiene un lote por id.
     *
     * @param plotId id del lote a obtener.
     * @return el lote.
     */
    @GetMapping("/{plotId}")
    public ResponseEntity<GetPlotDto> getPlotById(@PathVariable Integer plotId) {
        return ResponseEntity.ok(plotService.getPlotById(plotId));

    }

    /**
     * Obtiene una lista de lotes por ID de propietario.
     *
     * @param ownerId id del propietario asignado al lote.
     * @return una lista de lotes.
     */
    @GetMapping("/{ownerId}/owner")
    public ResponseEntity<List<GetPlotDto>> getPlotByOwnerId(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(plotService.getPlotByOwnerId(ownerId));
    }
}
