package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Total area in m2 cannot be null")
    private double total_area_in_m2;

    @NotNull(message = "Built area in m2 cannot be null")
    private double built_area_in_m2;

    @NotNull(message = "Plot state id cannot be null")
    private int plot_state_id;

    @NotNull(message = "Plot type id cannot be null")
    private int plot_type_id;

    @NotNull(message = "User update id cannot be null")
    private Integer userUpdateId;
}
