package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Cliente para el servicio de archivos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileClient {
    /**
     * El identificador del archivo.
     */
    @JsonProperty(namespace = "uuid")
    private UUID uuid;
}
