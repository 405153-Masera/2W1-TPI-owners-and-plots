package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * La clase {@code PlotOwnerEntity} representa la tabla intermedia entre plot y owner.
 * Referencia a la tabla llamada "plotowners".
 */
@Entity
@Table(name = "plot_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotOwnerEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador único de un lote.
     */
    @ManyToOne
    @JoinColumn(name = "plot_id")
    private PlotEntity plot;

    /**
     * Identificador único de un propietario, representa la relación muchos a uno entre
     * la tabla plotowners y owners.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    /**
     * Fecha que representa cuando se creó la entidad.
     */
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    /**
     * Identificador que representa el usuario que creó la entidad.
     */
    @Column(name = "created_user")
    private Integer createdUser;

    /**
     * Fecha que representa cuando fue la última vez que se modificó la entidad.
     */
    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    /**
     * Identificador que representa el usuario que modificó por ultima vez.
     */
    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}
