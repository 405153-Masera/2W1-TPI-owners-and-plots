package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.OwnerStatsService;
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
}
