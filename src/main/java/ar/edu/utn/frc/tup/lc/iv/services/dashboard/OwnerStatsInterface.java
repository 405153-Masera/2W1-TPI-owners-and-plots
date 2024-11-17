package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que contiene la lógica de negocio para obtener estadísticas de los propietarios y
 * de los lotes.
 */
@Service
public interface OwnerStatsInterface {

    /**
     * Obtiene una lista de datos de las manzanas.
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return una lista de datos de las manzanas
     */
    List<BlockData> getBlocksData(LocalDate startDate, LocalDate endDate);
}
