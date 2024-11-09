package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la información de una manzana.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockData {

    /**
     * Número de manzana.
     */
    private int blockNumber;

    /**
     * Aréa total de la manzana.
     */
    private double totalArea;

    /**
     * Aréa construida de la manzana.
     */
    private double builtArea;
}
