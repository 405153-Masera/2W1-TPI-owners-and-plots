package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Conecta la aplicación con la base de datos para manejar propietarios.
 */
@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, Integer> {

    /**
     * Busca un propietario por el id de un lote.
     *
     * @param plotId el id de un lote.
     * @return una lista de {@link OwnerEntity}
     */
    @Query("SELECT o FROM OwnerEntity o JOIN PlotOwnerEntity po ON o.id = po.owner.id WHERE po.plot.id = :plotId")
    Optional<List<OwnerEntity>> findByPlotId(Integer plotId);

    /**
     * Busca a todos los propietarios activos.
     *
     * @return una lista de {@link OwnerEntity}
     */
    @Query("SELECT o FROM OwnerEntity o WHERE o.active = true")
    List<OwnerEntity> findAllActives();

    /**
     * Busca un propietario por su id y estado.
     *
     * @param id     el identificador de un propietario.
     * @param active el estado a buscar.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByIdAndActive(Integer id, Boolean active);

    /**
     * Busca un propietario por su id.
     *
     * @param dni el identificador de un propietario.
     * @return un {@link OwnerEntity}
     */
    OwnerEntity findByDni(String dni);
}
