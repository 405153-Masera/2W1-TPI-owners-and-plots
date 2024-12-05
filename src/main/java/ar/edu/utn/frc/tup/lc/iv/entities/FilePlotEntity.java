package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * La clase {@code FilePlotEntity} representa la tabla intermedia entre file y plot.
 * Referencia a la tabla llamada "filesplots".
 */
@Entity
@Table(name = "files_plots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePlotEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Identificador único de un archivo.
     * Representa la relación muchos a uno entre la tabla filesplots y files.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private FileEntity file;

    /**
     * Identificador único de un lote.
     * Representa la relación muchos a uno entre la tabla filesplots y plots.
     */
    @ManyToOne
    @JoinColumn(name = "plot_id")
    private PlotEntity plot;

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
