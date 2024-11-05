package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.TaxStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar la situación fiscal
 * de los propietarios.
 */
@Repository
public interface TaxStatusRepository extends JpaRepository<TaxStatusEntity, Integer> {
}
