package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa las estadísticas de lotes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsStats {

    /**
     * Cantidad total de lotes.
     */
    private long totalPlots;

    /**
     * Cantidad de lotes disponibles.
     */
    private long availablePlots;

    /**
     * Cantidad de lotes en construcción.
     */
    private long constructionPlots;

    /**
     * Cantidad de lotes ocupados.
     */
    private long occupiedPlots;

    /**
     * Cantidad de lotes en venta.
     */
    private double totalArea;

    /**
     * Área total.
     */
    private double constructedArea;
}
