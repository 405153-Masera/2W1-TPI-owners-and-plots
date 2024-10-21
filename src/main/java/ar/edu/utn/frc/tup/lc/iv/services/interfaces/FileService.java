package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileService {

    /**
     * Obtiene una lista de archivos.
     *
     * @param plotId el id del lote
     * @return una lista de archivos
     */
    List<FileDto> getPlotFiles(Integer plotId);

    /**
     * Obtiene una lista de archivos.
     *
     * @param ownerId el id del propietario
     * @return una lista de archivos
     */
    List<FileDto> getOwnerFiles(Integer ownerId);
}
