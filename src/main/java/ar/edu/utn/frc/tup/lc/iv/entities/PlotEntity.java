package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "plot_number")
    private Integer plotNumber;

    @Column(name = "block_number")
    private Integer blockNumber;

    @Column(name = "total_area_in_m2")
    private Double totalAreaInM2;

    @Column(name = "built_area_in_m2")
    private Double builtAreaInM2;

    @ManyToOne
    @JoinColumn(name = "plot_state_id")
    private PlotStateEntity plotState;

    @ManyToOne
    @JoinColumn(name = "plot_type_id")
    private PlotTypeEntity plotType;

    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Column(name = "created_user")
    private Integer createdUser;

    @Column(name = "last_updated_datetime")
    private LocalDateTime lastUpdatedDatetime;

    @Column(name = "last_updated_user")
    private Integer lastUpdatedUser;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    private List<FilePlotEntity> files = new ArrayList<>();
}
