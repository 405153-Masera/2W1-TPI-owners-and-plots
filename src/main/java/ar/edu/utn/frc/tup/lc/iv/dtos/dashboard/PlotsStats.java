package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa las estadísticas de los terrenos en una determinada área o proyecto.
 * Incluye información sobre el total de terrenos, los disponibles, en construcción, ocupados,
 * así como las áreas total y construida.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsStats {


    /**
     * Total de terrenos en el área o proyecto.
     */
    private long totalPlots;

    /**
     * Número de terrenos disponibles en el área o proyecto.
     */
    private long availablePlots;

    /**
     * Número de terrenos en construcción en el área o proyecto.
     */
    private long constructionPlots;

    /**
     * Número de terrenos ocupados en el área o proyecto.
     */
    private long occupiedPlots;

    /**
     * Área total de todos los terrenos en el área o proyecto.
     */
    private double totalArea;

    /**
     * Área construida en los terrenos en el área o proyecto.
     */
    private double constructedArea;
}
