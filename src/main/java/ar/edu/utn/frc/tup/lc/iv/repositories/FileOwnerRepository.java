package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.FileOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar operaciones CRUD relacionadas con la entidad FileOwnerEntity.
 * Extiende JpaRepository para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface FileOwnerRepository extends JpaRepository<FileOwnerEntity, Integer> {
}
