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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code OwnerEntity} representa un propietario.
 * Referencia a la tabla llamada "owners".
 */
@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerEntity {

    /**
     * Identificador único de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del propietario.
     */
    private String name;

    /**
     * Apellido del propietario.
     */
    private String lastname;

    /**
     * Número de DNI del propietario.
     */
    private String dni;

    /**
     * Identificador de tipo de dni del usuario.
     */
    @ManyToOne
    @JoinColumn(name = "dni_type_id")
    private DniTypeEntity dni_type_id;

    /**
     * Fecha de nacimiento del propietario.
     */
    @Column(name = "date_birth")
    private LocalDate dateBirth;

    /**
     * Identificador único del tipo de propietario.
     * Representa la relación muchos a uno entre la tabla owners y ownerstypes.
     */
    @ManyToOne
    @JoinColumn(name = "owner_type_id")
    private OwnerTypeEntity ownerType;

    /**
     * Identificador único de la situación fiscal del propietario.
     * Representa la relación muchos a uno entre la tabla owners y taxstatus.
     */
    @ManyToOne
    @JoinColumn(name = "tax_status_id")
    private TaxStatusEntity taxStatus;

    /**
     * Nombre de la empresa del propietario.
     */
    @Column(name = "business_name")
    private String businessName;

    /**
     * Representa sí el propietario está activo o no.
     */
    private Boolean active;

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
     * Lista de los archivos que tiene el propietario.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileOwnerEntity> files = new ArrayList<>();
}
