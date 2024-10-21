package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Conecta la aplicación con la base de datos para manejar archivos.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    /**
     * Busca una lista de archivos por el id de un lote.
     *
     * @param plotId id del lote.
     * @return una lista de {@link FileEntity}.
     */
    @Query("SELECT f FROM FileEntity f " + "JOIN FilePlotEntity fp ON fp.file.id = f.id " + "WHERE fp.plot.id = :plotId")
    List<FileEntity> findFileByPlotId(Integer plotId);

    /**
     * Busca una lista de archivos por el id de un propietario.
     *
     * @param ownerId id del propietario.
     * @return lista de {@link FileEntity}.
     */
    @Query("SELECT f FROM FileEntity f " + "JOIN FileOwnerEntity fo ON fo.file.id = f.id " + "WHERE fo.owner.id = :ownerId")
    List<FileEntity> findFileByOwnerId(Integer ownerId);
}
