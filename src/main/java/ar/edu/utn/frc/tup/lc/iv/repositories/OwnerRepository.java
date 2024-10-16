package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, Integer> {

    @Query("SELECT o FROM OwnerEntity o JOIN PlotOwnerEntity po ON o.id = po.owner.id WHERE po.plot.id = :plotId")
    Optional<List<OwnerEntity>> findByPlotId(Integer plotId);
}
