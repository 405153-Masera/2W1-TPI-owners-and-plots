package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRequestDTO {
    /**
     * Identificador único de la entidad.
     */
    private Integer id;

    /**
     * Nombre asociado al tipo de entidad.
     */
    @NotNull(message = "El nombre no puede ser nula")
    private String name;

    /**
     * Dirección de correo electrónico de contacto.
     */
    private String email;

    /**
     * Número de teléfono de contacto.
     */
    private String phone;

    /**
     * Indica si la persona ha sido contactada.
     */
    private Boolean contacted;

    /**
     * Identificador que representa el ID del lote asociado.
     */
    @NotNull(message = "El lot_id no puede ser nula")
    @JsonProperty("plot_id")
    private Integer lotId;
    /**
     * Identificador que representa el numero de lote.
     */
    @JsonProperty("plot_number")
    private Integer plotNumber;
    /**
     * Identificador que representa el numero de manzana.
     */
    @JsonProperty("block_number")
    private Integer blockNumber;

    /**
     * Observaciones de la solicitud
     */
    private String observations;

    /**
     * Fecha que representa cuando se creó la solicitud.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("request_date")
    private LocalDateTime requestDate;

}
