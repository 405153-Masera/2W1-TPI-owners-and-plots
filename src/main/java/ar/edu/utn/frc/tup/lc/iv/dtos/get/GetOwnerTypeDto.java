package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * un tipo de propietario.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetOwnerTypeDto {

    /**
     * Identificador único del tipo de propietario.
     */
    private int id;

    /**
     * Nombre del tipo de propietario.
     */
    private String description;
}
