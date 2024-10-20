package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * un propietario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * DNI del propietario.
     */
    private String dni;

    /**
     * CUIT/CUIL del propietario.
     */
    private String cuitCuil;

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
     * Lista de archivos del propietario.
     */
    private List<FileDto> files;
}
