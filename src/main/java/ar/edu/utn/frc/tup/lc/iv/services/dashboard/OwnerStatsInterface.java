package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return Una lista de datos de las manzanas.
     */
    List<BlockData> getBlocksData(LocalDate startDate, LocalDate endDate);

    /**
     * Obtiene el porcentaje de propietarios por estado fiscal.
     *
     * @return Un mapa donde la clave es el estado fiscal y el valor es el porcentaje de propietarios en ese estado.
     */
    Map<String, Double> getOwnerPercentageByTaxStatus();

    /**
     * Obtiene el conteo de propietarios por estado (activo/inactivo) por mes.
     *
     * @return Un mapa donde la clave es el nombre del mes y el valor es otro mapa con el estado (activo/inactivo)
     *         y el conteo de propietarios en ese estado.
     */
    Map<String, Map<String, Long>> getOwnerCountByStatusPerMonth();

    /**
     * Cuenta la cantidad de lotes por estado.
     *
     * @return Una lista de DTOs con el conteo de lotes por estado.
     */
    List<PlotByPlotStateCountDTO> countPlotsByState();

    /**
     * Cuenta la cantidad de lotes por tipo.
     *
     * @return Una lista de DTOs con el conteo de lotes por tipo.
     */
    List<PlotByPlotTypeCountDTO> countPlotsByType();

    /**
     * Obtiene las estadísticas de los lotes.
     *
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @param plotType Tipo de lote.
     * @param plotStatus Estado del lote.
     * @return Un DTO con las estadísticas de los lotes.
     */
    PlotsStats getStatsOfPlots(LocalDate startDate, LocalDate endDate, String plotType, String plotStatus);

    /**
     * Obtiene los lotes por manzana.
     *
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return Una lista de DTOs con los lotes organizados por manzana.
     */
    List<PlotsByBlock> getPlotsByBlock(LocalDate startDate, LocalDate endDate);

    /**
     * Obtiene la distribución de los lotes por propietario.
     *
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @param plotType Tipo de lote.
     * @param plotStatus Estado del lote.
     * @return Una lista de DTOs con la distribución de lotes por propietario.
     */
     List<OwnersPlotsDistribution> getOwnersPlotsDistribution(LocalDate startDate, LocalDate endDate, String plotType, String plotStatus);

    /**
     * Obtiene las construcciones a lo largo de los años.
     *
     * @return Una lista de DTOs con el progreso de las construcciones.
     */
    List<ConstructionProgress> getConstructionProgress();

    /**
     * Crea la distribución de los lotes por propietario.
     *
     * @param plotOwners Una lista de entidades de propietarios de lotes.
     * @return Una lista de DTOs con la distribución de los lotes por propietario.
     */
    List<OwnersPlotsDistribution> createOwnersPlotsDistribution(List<PlotOwnerEntity> plotOwners);

    /**
     * Filtra los lotes según los parámetros recibidos.
     *
     * @param startDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @param plotType Tipo de lote.
     * @param plotStatus Estado del lote.
     * @return Una lista de entidades de lotes que cumplen con los filtros.
     */
    List<PlotEntity> getFiltersPlots(LocalDate startDate, LocalDate endDate, String plotType, String plotStatus);
}
