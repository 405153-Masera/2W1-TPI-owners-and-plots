package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.BlockData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * Obtiene el porcentaje de propietarios por estado fiscal.
     *
     * @return un mapa donde la clave es el estado fiscal y el valor es el porcentaje de propietarios en ese estado.
     */
    Map<String, Double> getOwnerPercentageByTaxStatus();

    /**
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes.
     *
     * @return un mapa donde la clave es el nombre del mes y el valor es otro mapa con el estado (activo/inactivo)
     * y el conteo de propietarios en ese estado.
     */
    Map<String, Map<String, Long>> getOwnerCountByStatusPerMonth();
}
