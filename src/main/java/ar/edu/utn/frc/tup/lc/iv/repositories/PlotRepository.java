package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Conecta la aplicación con la base de datos para manejar lotes.
 */
@Repository
public interface PlotRepository extends JpaRepository<PlotEntity, Integer> {

    /**
     * Busca un lote por el número de lote.
     *
     * @param plotNumber el número del lote.
     * @return un {@link PlotEntity}
     */
    PlotEntity findByPlotNumber(int plotNumber);

    /**
     * Busca una lista de lotes que estén disponibles.
     *
     * @return una lista de {@link PlotEntity}.
     */
    @Query("SELECT p FROM PlotEntity p WHERE p.plotState.name = 'Disponible'")
    List<PlotEntity> findPlotsAvailables();
}
