package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostPlotDto {

    @NotNull(message = "Plot number cannot be null")
    private int plot_number;

    @NotNull(message = "Block number cannot be null")
    private int block_number;

    @NotNull(message = "Total area in m2 cannot be null")
    private double total_area_in_m2;

    @NotNull(message = "Built area in m2 cannot be null")
    private double built_area_in_m2;

    @NotNull(message = "Plot state id cannot be null")
    private int plot_state_id;

    @NotNull(message = "Plot type id cannot be null")
    private int plot_type_id;

    @NotNull(message = "User create id cannot be null")
    private Integer userCreateId;

    private List<MultipartFile> files = new ArrayList<>();
}
