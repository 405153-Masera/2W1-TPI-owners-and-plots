package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la distribución de terrenos en un bloque específico.
 * Incluye información sobre los terrenos ocupados, disponibles, en construcción y el total en el bloque.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsByBlock {

    /**
     * Número del bloque.
     */
    private long blockNumber;

    /**
     * Cantidad de terrenos ocupados en el bloque.
     */
    private long occupied;

    /**
     * Cantidad de terrenos disponibles en el bloque.
     */
    private long available;

    /**
     * Cantidad de terrenos en construcción en el bloque.
     */
    private long inConstruction;

    /**
     * Cantidad total de terrenos en el bloque.
     */
    private long total;
}
