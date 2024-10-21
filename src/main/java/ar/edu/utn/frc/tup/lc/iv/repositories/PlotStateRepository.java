package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar los estados de los lotes.
 */
@Repository
public interface PlotStateRepository extends JpaRepository<PlotStateEntity, Integer> {
}
