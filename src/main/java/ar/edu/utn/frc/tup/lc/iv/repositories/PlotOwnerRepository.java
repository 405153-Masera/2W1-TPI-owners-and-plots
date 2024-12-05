package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    List<PlotOwnerEntity> findAllByOwner_Id(int ownerId);

    /**
     * Busca un PlotOwnerEntity por el id de un lote.
     *
     * @param plotId el id de un lote.
     * @return una lista de {@link PlotOwnerEntity}
     */
    PlotOwnerEntity findByPlotId(int plotId);

    /**
     * Elimina un PlotOwnerEntity por el id de un propietario y el id de un lote.
     *
     * @param ownerId el id de un propietario.
     * @param plotId el id de un lote.
     */
    void deleteByOwnerIdAndPlotId(int ownerId, int plotId);

    /**
     * Verifica si existe un PlotOwnerEntity por el id de un propietario.
     *
     * @param ownerId el id de un propietario.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByOwnerId(int ownerId);
}
