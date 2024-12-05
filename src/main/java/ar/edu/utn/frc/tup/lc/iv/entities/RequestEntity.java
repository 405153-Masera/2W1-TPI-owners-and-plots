package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * DTO que representa la solicitud para la compra de un lote
 */
@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestEntity {
    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre asociado al tipo de entidad.
     */
    @Column(name = "name")
    private String name;

    /**
     * Dirección de correo electrónico de contacto.
     */
    @Column(name = "email")
    private String email;

    /**
     * Número de teléfono de contacto.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Indica si la persona ha sido contactada.
     */
    @Column(name = "contacted")
    private Boolean contacted;

    /**
     * Identificador que representa el ID del lote asociado.
     */
    @Column(name = "lot_id")
    private Integer lotId;

    /**
     * Observaciones de la solicitud
     */
    @Column(name = "observations")
    private String observations;

    /**
     * Fecha que representa cuando se creó la solicitud.
     */
    @Column(name = "request_date")
    private LocalDateTime requestDate;
}
