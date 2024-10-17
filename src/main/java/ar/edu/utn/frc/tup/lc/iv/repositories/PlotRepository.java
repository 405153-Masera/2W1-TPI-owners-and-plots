package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlotRepository extends JpaRepository<PlotEntity, Integer> {
    PlotEntity findByPlotNumber(int plotNumber);

    @Query("SELECT p FROM PlotEntity p WHERE p.plotState.name = 'Disponible'")
    List<PlotEntity> findPlotsAvailables();
}
