package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "taxstatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;
}
