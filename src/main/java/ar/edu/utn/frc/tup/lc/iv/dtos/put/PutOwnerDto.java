package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa la solicitud para actualizar un propietario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutOwnerDto {

    /**
     * Nombre del propietario.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    /**
     * Apellido del propietario.
     */
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 50, message = "Lastname must be between 1 and 50 characters")
    private String lastname;

    /**
     * DNI del propietario.
     */
    @NotBlank(message = "DNI cannot be blank")
    private String dni;

    /**
     * CUIT/CUIL del propietario.
     */
    @NotBlank(message = "CUIT/CUIL cannot be blank")
    private String cuitCuil;

    /**
     * Correo electrónico del usuario utilizado en el login que se
     * actualiza en el microservicio de contactos.
     */
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is invalid")
    private String email;

    /**
     * Teléfono del usuario que se actualiza en el microservicio de contactos.
     */
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d+", message = "Phone number must be numeric")
    private String phoneNumber;

    /**
     * Fecha de nacimiento del propietario.
     */
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateBirth;

    /**
     * Identificador del tipo de propietario.
     */
    @NotNull(message = "Owner type cannot be null")
    private Integer ownerTypeId;

    /**
     * Identificador de la situación fiscal.
     */
    @NotNull(message = "Tax status cannot be null")
    private Integer taxStatusId;

    /**
     * Nombre del negocio del propietario.
     */
    @Size(min = 1, max = 50, message = "Business name must be between 1 and 50 characters")
    private String businessName;

    /**
     * Representa sí el propietario está activo o no.
     */
    @NotNull(message = "Active status cannot be null")
    private Boolean active;


    /**
     * Identificador del usuario que actualiza el propietario.
     */
    @NotNull(message = "User update ID cannot be null")
    private Integer userUpdateId;

    /**
     * Lista de archivos que se van a asignar al propietario.
     */
    private List<MultipartFile> files = new ArrayList<>();
}
