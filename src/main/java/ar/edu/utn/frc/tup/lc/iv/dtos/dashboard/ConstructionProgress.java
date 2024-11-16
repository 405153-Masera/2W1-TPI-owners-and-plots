package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa el progreso de construcción en un año específico.
 * Incluye el año y la cantidad de terrenos asociados al progreso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionProgress {

    /**
     * Año del progreso de construcción.
     */
    private long year;

    /**
     * Cantidad de terrenos asociados al progreso en el año especificado.
     */
    private long plotsCount;
}
