package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interfaz que contiene la lógica de negocio para obtener estadísticas de los propietarios y
 * de los lotes.
 */
@Service
public interface OwnerStatsInterface {

    /**
     * Obtiene una lista de datos de las manzanas.
     *
     * @return una lista de datos de las manzanas
     */
    List<BlockData> getBlocksData();
}
