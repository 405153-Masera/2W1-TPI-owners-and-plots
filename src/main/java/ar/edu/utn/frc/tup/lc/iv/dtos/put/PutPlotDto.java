package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutPlotDto {

    /*De momento contemplo que el numero de lote y manzano no se puede editar*/
//    private int plot_number;
//    private int block_number;
    private double total_area_in_m2;
    private double built_area_in_m2;
    private int plot_state_id;
    private int plot_type_id;

    private Integer userUpdateId;
}
