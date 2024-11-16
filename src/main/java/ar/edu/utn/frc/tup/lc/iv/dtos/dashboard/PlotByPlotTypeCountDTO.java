package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la cantidad de terrenos agrupados por su tipo.
 * Incluye el tipo de terreno y el conteo correspondiente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotByPlotTypeCountDTO {

    /**
     * Tipo de terreno.
     */
    private String type;

    /**
     * Cantidad de terrenos de este tipo.
     */
    private long count;
}

