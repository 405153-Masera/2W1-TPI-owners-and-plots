package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.PlotByPlotStateCountDTO;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.OwnerStatsService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para manejar las operaciones
 * relacionadas con el dashboard.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


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
    public ResponseEntity<List<BlockData>> getOwnerCountByRole() {
        List<BlockData> stats = ownerStatsService.getBlocksData();
        return ResponseEntity.ok(stats);
    }


    /**
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes
     * @return un mapa donde la clave es el mes y el valor es otro mapa con el estado
     * y el conteo de los propietarios
     */

    @GetMapping("/count-by-status-per-month")
    public Map<String, Map<String, Long>> getOwnersCountByStatusPerMonth() {
        return ownerStatsService.getOwnerCountByStatusPerMonth();
    }

    /**
     * Obtiene el porcentaje de propietarios por estado fiscal
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


}
