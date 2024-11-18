package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.DniTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar operaciones CRUD relacionadas con la entidad DniTypeEntity.
 * Extiende JpaRepository para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface DniTypeRepository extends JpaRepository<DniTypeEntity, Integer> {
}
