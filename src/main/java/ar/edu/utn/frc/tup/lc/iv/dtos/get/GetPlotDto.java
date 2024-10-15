package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPlotDto {
    private int id;
    private int plot_number;
    private int block_number;
    private double total_area_in_m2;
    private double built_area_in_m2;
    private String plot_state;
    private String plot_type;
}
