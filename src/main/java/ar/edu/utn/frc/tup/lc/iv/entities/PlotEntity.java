package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code PlotEntity} representa un lote.
 * Referencia a la tabla llamada "plots".
 */
@Entity
@Table(name = "plots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Número de lote.
     */
    @Column(name = "plot_number")
    private Integer plotNumber;

    /**
     * Número de manzana.
     */
    @Column(name = "block_number")
    private Integer blockNumber;

    /**
     * Metros cuadrados totales del lote.
     */
    @Column(name = "total_area_in_m2")
    private Double totalAreaInM2;

    /**
     * Metros cuadrados construidos del lote.
     */
    @Column(name = "built_area_in_m2")
    private Double builtAreaInM2;

    /**
     * Identificador único del estado del lote.
     * Representa la relación muchos a uno entre la tabla plots y plotstates.
     */
    @ManyToOne
    @JoinColumn(name = "plot_state_id")
    private PlotStateEntity plotState;

    /**
     * Identificador único del tipo de lote.
     * Representa la relación muchos a uno entre la tabla plots y plottypes.
     */
    @ManyToOne
    @JoinColumn(name = "plot_type_id")
    private PlotTypeEntity plotType;

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

    /**
     * Lista de archivos asociados al lote.
     */
    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilePlotEntity> files = new ArrayList<>();
}
