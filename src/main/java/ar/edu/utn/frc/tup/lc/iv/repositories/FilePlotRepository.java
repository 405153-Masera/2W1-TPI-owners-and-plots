package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que define los métodos de acceso a los datos de los lotes (PlotEntity).
 */
@Repository
public interface FilePlotRepository extends JpaRepository<PlotEntity, Integer> {
}
