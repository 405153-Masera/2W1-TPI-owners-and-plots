package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * el tipo de lote.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPlotTypeDto {

    /**
     * Identificador único del tipo de lote.
     */
    private int id;

    /**
     * Nombre del tipo de lote.
     */
    private String name;
}
