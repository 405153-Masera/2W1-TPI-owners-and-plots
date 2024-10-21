package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa la solicitud para crear un lote.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostPlotDto {

    /**
     * Numero de lote.
     */
    @NotNull(message = "Plot number cannot be null")
    private int plot_number;

    /**
     * Numero de manzana.
     */
    @NotNull(message = "Block number cannot be null")
    private int block_number;

    /**
     * Metros cuadrados totales del lote.
     */
    @NotNull(message = "Total area in m2 cannot be null")
    private double total_area_in_m2;

    /**
     * Metros cuadrados construidos del lote.
     */
    @NotNull(message = "Built area in m2 cannot be null")
    private double built_area_in_m2;

    /**
     * Identificador del estado del lote.
     */
    @NotNull(message = "Plot state id cannot be null")
    private int plot_state_id;

    /**
     * Identificador del tipo de lote.
     */
    @NotNull(message = "Plot type id cannot be null")
    private int plot_type_id;

    /**
     * Identificador del usuario que crea el lote.
     */
    @NotNull(message = "User create id cannot be null")
    private Integer userCreateId;

    /**
     * Lista de archivos que se van a asignar al lote.
     */
    private List<MultipartFile> files = new ArrayList<>();
}
