package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa el progreso de la construcción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionProgress {

    /**
     * Año de la construcción.
     */
    private long year;

    /**
     * Cantidad de lotes.
     */
    private long plotsCount;
}
