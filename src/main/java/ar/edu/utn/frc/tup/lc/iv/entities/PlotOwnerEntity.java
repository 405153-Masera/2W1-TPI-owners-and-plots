package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plotowners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotOwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "plot_id")
    private PlotEntity plot;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

}
