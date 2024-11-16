package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la cantidad de terrenos agrupados por su estado.
 * Incluye el estado y el conteo correspondiente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotByPlotStateCountDTO {

    /**
     * Estado del terreno.
     */
    private String state;

    /**
     * Cantidad de terrenos en el estado especificado.
     */
    private long count;
}

