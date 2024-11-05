package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO que representa una respuesta que contiene la información de
 * un propietario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDto {

    /**
     * Identificador único del propietario.
     */
    private Integer id;

    /**
     * Nombre del propietario.
     */
    private String name;

    /**
     * Apellido del propietario.
     */
    private String lastname;

    /**
     * Tipo de DNI del propietario.
     */
    private String dni_type;

    /**
     * DNI del propietario.
     */
    private String dni;


    /**
     * Fecha de nacimiento del propietario.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateBirth;

    /**
     * Nombre del negocio del propietario.
     */
    private String businessName;

    /**
     * Representa sí el propietario está activo o no.
     */
    private Boolean active;

    /**
     * Tipo de propietario (persona física o jurídica).
     */
    private String ownerType;

    /**
     * Situación fiscal del propietario.
     */
    private String taxStatus;

    /**
     * Lista de archivos asociados al propietario.
     */
    private List<FileDto> files;
}
