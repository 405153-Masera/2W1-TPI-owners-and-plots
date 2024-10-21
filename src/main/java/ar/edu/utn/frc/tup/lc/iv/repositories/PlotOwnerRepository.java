package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la base de datos para manejar la tabla intermedia plotowners.
 */
@Repository
public interface PlotOwnerRepository extends JpaRepository<PlotOwnerEntity, Integer> {

    /**
     * Busca un PlotOwnerEntity por el id de un propietario.
     *
     * @param ownerId el id de un propietario.
     * @return un {@link PlotOwnerEntity}
     */
    PlotOwnerEntity findByOwnerId(int ownerId);
}
