package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la cantidad de lotes por manzana.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsByBlock {

    /**
     * Número de manzana.
     */
    private long blockNumber;

    /**
     * Cantidad de lotes.
     */
    private long occupied;

    /**
     * Cantidad de lotes disponibles.
     */
    private long available;

    /**
     * Cantidad de lotes en construcción.
     */
    private long inConstruction;

    /**
     * Cantidad total de lotes.
     */
    private long total;
}
