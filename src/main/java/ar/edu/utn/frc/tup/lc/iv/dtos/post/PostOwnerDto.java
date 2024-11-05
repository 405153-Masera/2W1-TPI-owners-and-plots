package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto que representa una solicitud para crear un propietario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOwnerDto {

    /**
     * Nombre del propietario.
     */
    @NotBlank(message = "El  nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;

    /**
     * Apellido del propietario.
     */
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    private String lastname;

    /**
     * Tipo de DNI del propietario.
     */
    @NotNull(message = "El tipo de DNI no puede ser nulo")
    private Integer dni_type_id;

    /**
     * DNI del propietario.
     */
    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, message = "El DNI debe tener al menos 8 caracteres")
    @Pattern(regexp = "\\d+", message = "El DNI debe contener solo números")
    private String dni;

    /**
     * Fecha de nacimiento del propietario.
     */
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate dateBirth;

    /**
     * Identificador del tipo de propietario.
     */
    @NotNull(message = "El tipo de propietario no puede ser nulo")
    private Integer ownerTypeId;

    /**
     * Identificador de la situación fiscal.
     */
    @NotNull(message = "El estado fiscal no puede ser nulo")
    private Integer taxStatusId;

    /**
     * Nombre del negocio del propietario.
     */
    @Size(max = 50, message = "El nombre del negocio debe tener como máximo 50 caracteres")
    private String businessName;

    /**
     * Representa sí el propietario está activo o no.
     */
    @NotNull(message = "El estado no puede ser nulo")
    private Boolean active;

    /**
     * Nombre de usuario utilizado en el login.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 1, max = 30, message = "El nombre de usuario debe tener entre 1 y 30 caracteres")
    private String username;

    /**
     * Contraseña del usuario utilizada en el login.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 30, message = "La contraseña debe tener entre 6 y 30 caracteres")
    private String password;

    /**
     * Correo electrónico del usuario utilizado en el login
     * que se guarda en el microservicio de contactos.
     */
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico es inválido")
    private String email;

    /**
     * Teléfono del usuario que se guarda en
     * el microservicio de contactos.
     */
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Size(min = 10, max = 20, message = "El número de teléfono debe tener entre 8 y 20 caracteres")
    @Pattern(regexp = "\\d+", message = "El número de teléfono debe ser numérico")
    private String phoneNumber;

    /**
     * Dirección URL del avatar asignado al usuario.
     */
    private String avatarUrl;

    /**
     * Lista de roles asignados al usuario.
     */
    @NotNull(message = "Los roles no pueden ser nulos")
    private String[] roles;

    /**
     * Identificador del lote asociado al propietario y al usuario.
     */
    @NotNull(message = "El ID del lote no puede ser nulo")
    private Integer[] plotId;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegramId;

    /**
     * Identificador del usuario que crea el propietario y el usuario.
     */
    @NotNull(message = "El ID del usuario creador no puede ser nulo")
    private Integer userCreateId;

    /**
     * Lista de archivos que se van a asignar al propietario.
     */
    private List<MultipartFile> files = new ArrayList<>();
}
