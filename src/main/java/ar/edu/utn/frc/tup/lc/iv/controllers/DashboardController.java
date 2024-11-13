package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.OwnerStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para manejar las operaciones
 * relacionadas con el dashboard.
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    /**
     * Servicio para manejar las estadísticas de los gráficos.
     */
    private final OwnerStatsService ownerStatsService;

    /**
     * Maneja las estadísticas de las manzanas.
     *
     * @return ResponseEntity con una lista de BlockData
     * que contiene datos de las manzanas.
     */
    @GetMapping("/blockStats")
    public ResponseEntity<List<BlockData>> getOwnerCountByRole(@RequestParam(required = false) LocalDate startDate,
                                                               @RequestParam(required = false) LocalDate endDate) {
        List<BlockData> stats = ownerStatsService.getBlocksData(startDate, endDate);
        return ResponseEntity.ok(stats);
    }


    /**
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes.
     * @return un mapa donde la clave es el mes y el valor es otro mapa con el estado
     * y el conteo de los propietarios
     */
    @GetMapping("/count-by-status-per-month")
    public Map<String, Map<String, Long>> getOwnersCountByStatusPerMonth() {
        return ownerStatsService.getOwnerCountByStatusPerMonth();
    }

    /**
     * Obtiene el porcentaje de propietarios por estado fiscal.
     * @return un mapa donde la clave es el estado fiscal y el valor es el porcentaje
     * de propietarios en ese estado
     */
    @GetMapping("/percentage-by-tax-status")
    public Map<String, Double> getOwnerPercentageByTaxStatus() {
        return ownerStatsService.getOwnerPercentageByTaxStatus();
    }


    @GetMapping("/Plot-By-State-Count")
    public ResponseEntity<List<PlotByPlotStateCountDTO>> getPlotByStateCount() {
        List<PlotByPlotStateCountDTO> stats = ownerStatsService.countPlotsByState();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/Plot-By-Type-Count")
    public ResponseEntity<List<PlotByPlotTypeCountDTO>> getPlotByTypeCount() {
        List<PlotByPlotTypeCountDTO> stats = ownerStatsService.countPlotsByType();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/plots-stats")
    public ResponseEntity<PlotsStats> getGeneralStats(@RequestParam(required = false) LocalDate startDate,
                                                      @RequestParam(required = false) LocalDate endDate,
                                                      @RequestParam(required = false) String plotType,
                                                      @RequestParam(required = false) String plotStatus) {
        return ResponseEntity.ok(ownerStatsService.getStatsOfPlots(startDate, endDate, plotType, plotStatus));
    }

    @GetMapping("/plots-by-block")
    public ResponseEntity<List<PlotsByBlock>> getPlotsByBlock(@RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(ownerStatsService.getPlotsByBlock(startDate, endDate));
    }

    @GetMapping("/owners-distribution")
    public ResponseEntity<List<OwnersPlotsDistribution>> getOwnershipDistribution(@RequestParam(required = false) LocalDate startDate,
                                                                                  @RequestParam(required = false) LocalDate endDate,
                                                                                  @RequestParam(required = false) String plotType,
                                                                                  @RequestParam(required = false) String plotStatus) {
        return ResponseEntity.ok(ownerStatsService.getOwnersPlotsDistribution(startDate, endDate, plotType, plotStatus));
    }

    @GetMapping("/construction-progress")
    public ResponseEntity<List<ConstructionProgress>> getConstructionProgress() {
        return ResponseEntity.ok(ownerStatsService.getConstructionProgress());
    }
}
