package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.OwnerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar los tipos de propietarios.
 */
@Repository
public interface OwnerTypeRepository extends JpaRepository<OwnerTypeEntity, Integer> {

}
