package ar.edu.utn.frc.tup.lc.iv.restTemplate.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO que representa la solicitud para crear un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPost {

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastname;

    /**
     * Nombre de usuario utilizado en el login.
     */
    private String username;

    /**
     * Contraseña del usuario utilizada en el login.
     */
    private String password;

    /**
     * Correo electronico del usuario utilizado en el login.
     */
    private String email;

    /**
     * Telefono del usuario.
     */
    private String phone_number;

    /**
     * Tipo de DNI del usuario.
     */
    private Integer dni_type_id;

    /**
     * Número de DNI del usuario.
     */
    private String dni;

    /**
     * Representa sí el usuario está activo o no.
     */
    private Boolean active;

    /**
     * Dirección URL del avatar asignado al usuario.
     */
    private String avatar_url;

    /**
     * Fecha de nacimiento del usuario.
     */
    private LocalDate datebirth;

    /**
     * Lista de los roles que tiene el usuario.
     */
    private String[] roles;

    /**
     * Identificador del usuario que crea el rol.
     */
    private Integer userUpdateId;

    /**
     * Identificador del lote asignado al usuario.
     */
    private Integer plot_id;

    /**
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Integer telegram_id;
}
