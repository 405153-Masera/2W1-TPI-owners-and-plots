package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la infromación del
 * tipo de documento que puede tener un propietario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDniTypeDto {

    /**
     * Identificador único del tipo de documento.
     */
    private int id;

    /**
     * Descripción del tipo de documento..
     */
    private String description;
}
