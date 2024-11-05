package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la información del
 * tipo de situación fiscal.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaxStatusDto {

    /**
     * Identificador único del tipo de situación fiscal.
     */
    private int id;

    /**
     * Nombre de la situación fiscal.
     */
    private String description;
}
