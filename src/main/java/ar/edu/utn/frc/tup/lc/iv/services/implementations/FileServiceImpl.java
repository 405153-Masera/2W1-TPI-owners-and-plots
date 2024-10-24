package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
import ar.edu.utn.frc.tup.lc.iv.entities.FileEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FilePlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz FileService,
 * contiene la lógica de archivos.
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * Repositorio para manejar File entities.
     */
    @Autowired
    private FileRepository fileRepository;

    /**
     * Repositorio para manejar FilePlot entities.
     */
    @Autowired
    private FilePlotRepository filePlotRepository;

    /**
     * Repositorio para manejar FileOwner entities.
     */
    @Autowired
    private FileOwnerRepository fileOwnerRepository;

    /**
     * Servicio para manejar la comunicación con el api de archivos.
     */
    @Autowired
    private FileManagerClient fileManagerClient;

    /**
     * Constructor de FileServiceImpl.
     *
     * @param fileRepository el repositorio de archivos
     * @param filePlotRepository el repositorio de archivos de lote
     * @param fileOwnerRepository el repositorio de archivos de propietario
     * @param fileManagerClient el servicio de archivos
     */
//    @Autowired
//    public FileServiceImpl(FileRepository fileRepository, FilePlotRepository filePlotRepository,
//                           FileOwnerRepository fileOwnerRepository, FileManagerClient fileManagerClient) {
//        this.fileRepository = fileRepository;
//        this.filePlotRepository = filePlotRepository;
//        this.fileOwnerRepository = fileOwnerRepository;
//        this.fileManagerClient = fileManagerClient;
//    }

    /**
     * Obtiene una lista de archivos.
     *
     * @param plotId el id del lote
     * @return una lista de archivos
     */
    @Override
    public List<FileDto> getPlotFiles(Integer plotId) {
        List<FileEntity> filesEntities = fileRepository.findFileByPlotId(plotId);
        List<FileDto> files = new ArrayList<>();
        if (filesEntities != null) {
            for (FileEntity file : filesEntities) {
                FileDto fileDto = new FileDto();
                fileDto.setName(file.getName());
                fileDto.setUuid(file.getFileUuid());
                files.add(fileDto);
            }
            return files;
        }
        return null;
    }

    /**
     * Obtiene una lista de archivos.
     *
     * @param ownerId el id del propietario
     * @return una lista de archivos
     */
    @Override
    public List<FileDto> getOwnerFiles(Integer ownerId) {
        List<FileEntity> filesEntities = fileRepository.findFileByOwnerId(ownerId);
        List<FileDto> files = new ArrayList<>();
        if (filesEntities != null) {
            for (FileEntity file : filesEntities) {
                FileDto fileDto = new FileDto();
                fileDto.setName(file.getName());
                fileDto.setUuid(file.getFileUuid());
                files.add(fileDto);
            }
            return files;
        }
        return null;
    }
}
