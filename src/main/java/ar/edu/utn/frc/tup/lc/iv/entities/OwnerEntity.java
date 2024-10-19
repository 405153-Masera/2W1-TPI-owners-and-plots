package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String lastname;
    private String dni;

    @Column(name = "cuit_cuil")
    private String cuitCuil;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @ManyToOne
    @JoinColumn(name = "owner_type_id")
    private OwnerTypeEntity ownerType;

    @ManyToOne
    @JoinColumn(name = "tax_status_id")
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<FileOwnerEntity> files = new ArrayList<>();
}
