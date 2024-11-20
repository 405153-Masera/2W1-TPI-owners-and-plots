package ar.edu.utn.frc.tup.lc.iv.restTemplate.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO que representa la solicitud para actualizar un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPut {

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Apellido del usuario.
     */
    private String lastName;

    /**
     * Número de DNI del usuario.
     */
    private Integer dni_type_id;

    /**
     * Número de DNI del usuario.
     */
    private String dni;

    /**
     * Teléfono del usuario que se modifica mediante en el microservicio de contactos.
     */
    private String phoneNumber;

    /**
     * Correo electrónico del usuario utilizado en el login que se modifica en el microservicio
     * de contactos.
     */
    private String email;

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
     * Identificador de la plataforma telegram utilizada en notificaciones.
     */
    private Long telegram_id;

    /**
     * Lote asignado al usuario.
     */
    private Integer[] plot_id;
}
