package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * el estado del lote.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPlotStateDto {

    /**
     * Identificador único del estado del lote.
     */
    private int id;

    /**
     * Nombre del estado del lote.
     */
    private String name;
}
