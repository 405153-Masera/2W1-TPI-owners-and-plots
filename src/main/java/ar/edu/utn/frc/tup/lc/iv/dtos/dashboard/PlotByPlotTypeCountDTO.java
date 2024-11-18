package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un Contador por tipo de lote.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotByPlotTypeCountDTO {

    /**
     * Tipo de lote.
     */
    private String type;

    /**
     * Cantidad de lotes de ese tipo.
     */
    private long count;
}
