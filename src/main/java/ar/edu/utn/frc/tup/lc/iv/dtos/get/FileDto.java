package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un archivo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    /**
     * Identificador único del archivo.
     */
    private String fileId;
}
