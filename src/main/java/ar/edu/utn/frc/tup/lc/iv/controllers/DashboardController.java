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
     * Obtiene el conteo de propietarios por manzana en un rango de fechas.
     *
     * @param startDate fecha de inicio del rango (opcional).
     * @param endDate   fecha de fin del rango (opcional).
     * @return ResponseEntity con una lista de BlockData
     *         que contiene datos de las manzanas.
     */
    @GetMapping("/blockStats")
    public ResponseEntity<List<BlockData>> getOwnerCountByRole(@RequestParam(required = false) LocalDate startDate,
                                                               @RequestParam(required = false) LocalDate endDate) {
        List<BlockData> stats = ownerStatsService.getBlocksData(startDate, endDate);
        return ResponseEntity.ok(stats);
    }



    /**
     * Obtiene el conteo mensual de propietarios por estado.
     *
     * @return Mapa con mes como clave y otro mapa (estado y conteo) como valor.
     */
    @GetMapping("/count-by-status-per-month")
    public Map<String, Map<String, Long>> getOwnersCountByStatusPerMonth() {
        return ownerStatsService.getOwnerCountByStatusPerMonth();
    }



    /**
     * Cuenta la cantidad de lotes por estado.
     *
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @param plotType Tipo de lote.
     * @return una lista de PlotByPlotStateCountDTO que contiene la cantidad de lotes por estado.
     */
    @GetMapping("/percentage-by-tax-status")
    public Map<String, Double> getOwnerPercentageByTaxStatus() {
        return ownerStatsService.getOwnerPercentageByTaxStatus();
    }

    /**
     * Obtiene el conteo de lotes por estado.
     *
     * @return Una lista de {@link PlotByPlotStateCountDTO}.
     */
    @GetMapping("/Plot-By-State-Count")
    public ResponseEntity<List<PlotByPlotStateCountDTO>> getPlotByStateCount(@RequestParam(required = false) LocalDate startDate,
                                                                             @RequestParam(required = false) LocalDate endDate,
                                                                             @RequestParam(required = false) Integer plotType) {
        List<PlotByPlotStateCountDTO> stats = ownerStatsService.countPlotsByState(startDate, endDate, plotType);
        return ResponseEntity.ok(stats);
    }


    /**
     * Cuenta la cantidad de lotes por tipo.
     *
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @return una lista de PlotByPlotTypeCountDTO que contiene la cantidad de lotes por tipo.
     */
    @GetMapping("/Plot-By-Type-Count")
    public ResponseEntity<List<PlotByPlotTypeCountDTO>> getPlotByTypeCount(@RequestParam(required = false) LocalDate startDate,
                                                                           @RequestParam(required = false) LocalDate endDate) {
        List<PlotByPlotTypeCountDTO> stats = ownerStatsService.countPlotsByType(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    /**
     * Maneja las estadísticas generales de los lotes como la cantidad de lotes
     * en construcción, disponibles, ocupados.
     *
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @param plotType Tipo de lote.
     * @param plotStatus Estado del lote.
     * @return una lista de PlotsStats que contiene las estadísticas generales de los lotes.
     */
    @GetMapping("/plots-stats")
    public ResponseEntity<PlotsStats> getGeneralStats(@RequestParam(required = false) LocalDate startDate,
                                                      @RequestParam(required = false) LocalDate endDate,
                                                      @RequestParam(required = false) String plotType,
                                                      @RequestParam(required = false) String plotStatus) {
        return ResponseEntity.ok(ownerStatsService.getStatsOfPlots(startDate, endDate, plotType, plotStatus));
    }

    /**
     * Maneja las estadísticas de los lotes por manzana.
     *
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @return una lista de PlotsByBlock que contiene las estadísticas de los lotes por manzana.
     */
    @GetMapping("/plots-by-block")
    public ResponseEntity<List<PlotsByBlock>> getPlotsByBlock(@RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(ownerStatsService.getPlotsByBlock(startDate, endDate));
    }

    /**
     * Maneja la distribución de los propietarios por manzana.
     *
     * @param startDate Fecha de inicio del rango de fechas.
     * @param endDate Fecha de fin del rango de fechas.
     * @param plotType Tipo de lote.
     * @param plotStatus Estado del lote.
     * @return una lista de OwnersPlotsDistribution que contiene las estadísticas de los propietarios por manzana.
     */
    @GetMapping("/owners-distribution")
    public ResponseEntity<List<OwnersPlotsDistribution>> getOwnershipDistribution(@RequestParam(required = false) LocalDate startDate,
                                                                                  @RequestParam(required = false) LocalDate endDate,
                                                                                  @RequestParam(required = false) String plotType,
                                                                                  @RequestParam(required = false) String plotStatus) {
        return ResponseEntity.ok(ownerStatsService.getOwnersPlotsDistribution(startDate, endDate, plotType, plotStatus));
    }

    /**
     * Obtiene el progreso de construcción.
     *
     * @return Una lista de {@link ConstructionProgress}.
     */
    @GetMapping("/construction-progress")
    public ResponseEntity<List<ConstructionProgress>> getConstructionProgress() {
        return ResponseEntity.ok(ownerStatsService.getConstructionProgress());
    }

}
