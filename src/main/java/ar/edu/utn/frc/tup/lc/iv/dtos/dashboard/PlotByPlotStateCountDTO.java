package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un Contador por estado de lote
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotByPlotStateCountDTO {
    private String state;
    private long count;
}