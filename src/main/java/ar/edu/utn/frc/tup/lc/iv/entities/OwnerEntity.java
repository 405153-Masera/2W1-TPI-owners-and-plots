package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private Integer dni;

    @Column(name = "cuit_cuil")
    private Integer cuitCuil;

    @Column(name = "date_birth")
    private LocalDateTime dateBirth;

    @ManyToOne
    @JoinColumn(name = "owner_type_id", referencedColumnName = "id")  // Agregamos la relación con OwnersTypes
    private OwnerTypeEntity ownerType;

    @ManyToOne
    @JoinColumn(name = "tax_status_id", referencedColumnName = "id")
    private TaxStatusEntity taxStatus;

    @Column(name = "business_name")
    private String businessName;
    private Boolean active;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}
