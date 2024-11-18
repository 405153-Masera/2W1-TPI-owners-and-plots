package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * La clase {@code PlotTypeEntity} representa un tipo de lote.
 * Referencia a la tabla llamada "plottypes".
 */
@Entity
@Table(name = "plottypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotTypeEntity {

    /**
     * Identificador único de un tipo de lote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del tipo de lote.
     */
    private String name;

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
