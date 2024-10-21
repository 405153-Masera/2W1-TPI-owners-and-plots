package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de archivos.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    /**
     * Obtiene una lista de archivos por id de plot.
     *
     * @param plotId id del plot.
     * @return lista de archivos.
     */
    @Query("SELECT f FROM FileEntity f " + "JOIN FilePlotEntity fp ON fp.file.id = f.id " + "WHERE fp.plot.id = :plotId")
    List<FileEntity> findFileByPlotId(Integer plotId);

    /**
     * Obtiene una lista de archivos por id de propietario.
     *
     * @param ownerId id del propietario.
     * @return lista de archivos.
     */
    @Query("SELECT f FROM FileEntity f " + "JOIN FileOwnerEntity fo ON fo.file.id = f.id " + "WHERE fo.owner.id = :ownerId")
    List<FileEntity> findFileByOwnerId(Integer ownerId);
}
