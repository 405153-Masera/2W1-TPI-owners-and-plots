package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
import ar.edu.utn.frc.tup.lc.iv.entities.FileEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FilePlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FileRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz FileService,
 * contiene la lógica de archivos.
 */
@Service
@Data
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    /**
     * Repositorio para manejar File entities.
     */
    private final FileRepository fileRepository;

    /**
     * Repositorio para manejar FilePlot entities.
     */
    private final FilePlotRepository filePlotRepository;

    /**
     * Repositorio para manejar FileOwner entities.
     */
    private final FileOwnerRepository fileOwnerRepository;

    /**
     * Servicio para manejar la comunicación con el api de archivos.
     */
    private final FileManagerClient fileManagerClient;

    /**
     * Obtiene una lista de archivos.
     *
     * @param plotId el id del lote
     * @return una lista de archivos
     */
    @Override
    public List<FileDto> getPlotFiles(Integer plotId) {
        return convertToDtoList(fileRepository.findFileByPlotId(plotId));
    }

    /**
     * Obtiene una lista de archivos.
     *
     * @param ownerId el id del propietario
     * @return una lista de archivos
     */
    @Override
    public List<FileDto> getOwnerFiles(Integer ownerId) {
        return convertToDtoList(fileRepository.findFileByOwnerId(ownerId));
    }

    /**
     * Convierte una lista de FileEntity a una lista de FileDto.
     *
     * @param filesEntities lista de entidades FileEntity
     * @return lista de objetos FileDto
     */
    private List<FileDto> convertToDtoList(List<FileEntity> filesEntities) {
        return filesEntities == null ? null : filesEntities.stream()
                .map(this::convertToFileDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad FileEntity a un objeto FileDto.
     *
     * @param file la entidad FileEntity a convertir
     * @return un objeto FileDto
     */
    private FileDto convertToFileDto(FileEntity file) {
        FileDto fileDto = new FileDto();
        fileDto.setName(file.getName());
        fileDto.setUuid(file.getFileUuid());
        return fileDto;
    }
}
