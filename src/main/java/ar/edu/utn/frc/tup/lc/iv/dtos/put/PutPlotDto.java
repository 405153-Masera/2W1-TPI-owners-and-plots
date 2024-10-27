package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa la solicitud para actualizar un lote.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutPlotDto {

    /*De momento contemplo que el numero de lote y manzano no se puede editar*/
//    private int plot_number;
//    private int block_number;
    /**
     * Metros cuadrados totales del lote.
     */
    @NotNull(message = "Tota area en metros cuadrados no puede ser nula")
    private double total_area_in_m2;

    /**
     * Metros cuadrados construidos del lote.
     */
    @NotNull(message = "Area construida en metros cuadrados no puede ser nula")
    private double built_area_in_m2;

    /**
     * Identificador del estado del lote.
     */
    @NotNull(message = "El estado del lote no puede ser nulo")
    private int plot_state_id;

    /**
     * Identificador del tipo de lote.
     */
    @NotNull(message = "El tipo de lote no puede ser nulo")
    private int plot_type_id;

    /**
     * Identificador del usuario que actualiza el lote.
     */
    @NotNull(message = "El ID del usuario creador no puede ser nulo")
    private Integer userUpdateId;

    /**
     * Lista de archivos que se van a asignar al lote.
     */
    private List<MultipartFile> files = new ArrayList<>();
}
