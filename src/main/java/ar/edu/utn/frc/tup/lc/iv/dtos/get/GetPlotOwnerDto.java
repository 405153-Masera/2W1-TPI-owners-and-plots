package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar la relación entre un lote y su propietario.
 * Contiene los identificadores del lote y del propietario correspondiente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPlotOwnerDto {

    /**
     * Identificador del lote.
     */
    private Integer plot_id;

    /**
     * Identificador del dueño del lote.
     */
    private Integer owner_id;
}
