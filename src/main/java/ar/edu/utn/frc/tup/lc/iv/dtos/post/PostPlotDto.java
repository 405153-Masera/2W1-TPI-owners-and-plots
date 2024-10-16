package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostPlotDto {
    private int plot_number;
    private int block_number;
    private double total_area_in_m2;
    private double built_area_in_m2;
    private int plot_state_id;
    private int plot_type_id;

    private Integer userCreateId;
}
