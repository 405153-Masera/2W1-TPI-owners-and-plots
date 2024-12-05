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
 * La clase {@code PlotStateEntity} representa un estado de un lote.
 * Referencia a la tabla llamada "plotstates".
 */
@Entity
@Table(name = "plot_states")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotStateEntity {

    /**
     * Identificador único de un estado de un lote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del estado del lote.
     */
    private String name;

    /**
     * Fecha que representa cuando se creó el estado del lote.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Fecha que representa cuando fué la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    /**
     * Identificador que representa el usuario que modificó la entidad por última vez.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}
