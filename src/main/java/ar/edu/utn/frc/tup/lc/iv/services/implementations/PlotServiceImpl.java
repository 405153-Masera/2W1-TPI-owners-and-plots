package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz PlotService,
 * contiene toda la lógica de los lotes.
 */
@Service
@Data
@RequiredArgsConstructor
public class PlotServiceImpl implements PlotService {

    /**
     * Repositorio para manejar Plot entities.
     */
    private final PlotRepository plotRepository;

    /**
     * Servicio para manejar la lógica de los propietarios de los lotes.
     */
    private final PlotOwnerService plotOwnerService;

    /**
     * Repositorio para manejar PlotState entities.
     */
    private final PlotStateRepository plotStateRepository;

    /**
     * Repositorio para manejar PlotType entities.
     */
    private final PlotTypeRepository plotTypeRepository;

    /**
     * Servicio para manejar la comunicación con el api de archivos.
     */
    private final FileManagerClient fileManagerClient;

    /**
     * Servicio para manejar la lógica de archivos.
     */
    private final FileService fileService;

    /**
     * Servicio para mapear entidades a dtos y viceversa.
     */
    private final ModelMapper modelMapper;

    /**
     * Repositorio para manejar PlotOwner entities.
     */
    private final PlotOwnerRepository plotOwnerRepository;
    private final OwnerRepository ownerRepository;

    /**
     * Crea un nuevo lote.
     *
     * @param postPlotDto datos del lote a guardar.
     * @return el lote creado.
     */
    @Override
    @Transactional
    public GetPlotDto createPlot(PostPlotDto postPlotDto) {
        validatePlotNumber(postPlotDto.getPlot_number());
        validateBuiltArea(postPlotDto);
        validateWasteland(postPlotDto);

        PlotEntity plotEntity = mapPlotPostToPlotEntity(postPlotDto);
        uploadFiles(postPlotDto.getFiles(), postPlotDto.getUserCreateId(), plotEntity);
        PlotEntity savedPlot = plotRepository.save(plotEntity);

        GetPlotDto getPlotDto = new GetPlotDto();
        mapPlotEntityToGetPlotDto(savedPlot, getPlotDto);
        return getPlotDto;
    }

    /**
     * Válida si el lote es de tipo valdio , no tenga metros construidos.
     *
     * @param postPlotDto Dto de solicitud de alta de lote.
     * @throws IllegalArgumentException si es de tipo baldio y tiene metros construidos.
     */
    public void validateWasteland(PostPlotDto postPlotDto) {
        if (postPlotDto.getPlot_type_id() == 3 && postPlotDto.getBuilt_area_in_m2() > 0) {
            throw new IllegalArgumentException("Error , a westland plot cannot have a built area");
        }
    }

    /**
     * Válida si el area construida es menor al area total del lote.
     *
     * @param postPlotDto Dto de solicitud de alta de lote.
     * @throws IllegalArgumentException si el area construida es mayor al area total.
     */
    public void validateBuiltArea(PostPlotDto postPlotDto) {
        if (postPlotDto.getBuilt_area_in_m2() > postPlotDto.getTotal_area_in_m2()) {
            throw new IllegalArgumentException("Error , built area is bigger than total area");
        }

    }

    /**
     * Sube archivos de un lote.
     *
     * @param files lista de archivos a subir.
     * @param userId id del usuario que sube los archivos.
     * @param plotEntity entidad del lote al que se le suben los archivos.
     */
    public void uploadFiles(List<MultipartFile> files, Integer userId, PlotEntity plotEntity) {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                FilePlotEntity filePlotEntity = createFilePlotEntity(file, userId, plotEntity);
                plotEntity.getFiles().add(filePlotEntity);
            }
        }
    }

    /**
     * Cambia el estado de un lote a vendido.
     *
     * @param plotId id del lote a vender.
     * @param userId id del usuario que actualiza.
     */
    @Override
    public void changePlotState(Integer plotId, Integer userId) {
        PlotEntity updatePlot = plotRepository.findById(plotId)
                .orElseThrow(() -> new EntityNotFoundException("Plot not found"));

        updatePlot.setPlotState(plotStateRepository.findById(2).orElseThrow(() -> new EntityNotFoundException("Plot State not found")));
        updatePlot.setLastUpdatedUser(userId);
        updatePlot.setLastUpdatedDatetime(LocalDateTime.now());
        plotRepository.save(updatePlot);
    }

    /**
     * Crea una entidad de archivo de lote.
     *
     * @param file archivo a subir.
     * @param userId id del usuario que sube el archivo.
     * @param plotEntity entidad del lote al que se le sube el archivo.
     * @return entidad de archivo de lote creada.
     */
    public FilePlotEntity createFilePlotEntity(MultipartFile file, Integer userId, PlotEntity plotEntity) {
        String fileUuid = fileManagerClient.uploadFile(file).getUuid().toString();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileUuid(fileUuid);
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setCreatedDatetime(LocalDateTime.now());
        fileEntity.setCreatedUser(userId);
        fileEntity.setLastUpdatedDatetime(LocalDateTime.now());
        fileEntity.setLastUpdatedUser(userId);

        FilePlotEntity filePlotEntity = new FilePlotEntity();
        filePlotEntity.setFile(fileEntity);
        filePlotEntity.setPlot(plotEntity);
        filePlotEntity.setCreatedDatetime(LocalDateTime.now());
        filePlotEntity.setCreatedUser(userId);
        filePlotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        filePlotEntity.setLastUpdatedUser(userId);

        return filePlotEntity;
    }


    /**
     * Obtiene los estados de los lotes.
     *
     * @return lista de estados de los lotes.
     */
    @Override
    public List<GetPlotStateDto> getPlotStates() {
        List<PlotStateEntity> plotStateEntities = plotStateRepository.findAll();
        List<GetPlotStateDto> plotStateDtos = new ArrayList<>();
        for (PlotStateEntity plotStateEntity : plotStateEntities) {
            plotStateDtos.add(modelMapper.map(plotStateEntity, GetPlotStateDto.class));
        }
        return plotStateDtos;
    }


    /**
     * Obtiene los tipos de los lotes.
     *
     * @return lista de tipos de los lotes.
     */
    @Override
    public List<GetPlotTypeDto> getPlotTypes() {
        List<PlotTypeEntity> plotTypeEntities = plotTypeRepository.findAll();
        List<GetPlotTypeDto> plotTypeDtos = new ArrayList<>();
        for (PlotTypeEntity plotTypeEntity : plotTypeEntities) {
            plotTypeDtos.add(modelMapper.map(plotTypeEntity, GetPlotTypeDto.class));
        }
        return plotTypeDtos;
    }

    /**
     * Actualiza un lote.
     *
     * @param plotDto datos del lote a actualizar.
     * @param plotId  id del lote a actualizar.
     * @throws RuntimeException si el lote no existe, si el estado y tipo del lote no existen.
     * @return el lote actualizado.
     */
    @Override
    @Transactional
    public GetPlotDto putPlot(PutPlotDto plotDto, Integer plotId) {
        PlotEntity plotEntity = getPlotEntityById(plotId);
        updatePlotFields(plotEntity, plotDto);
        plotRepository.save(plotEntity);

        uploadFiles(plotDto.getFiles(), plotDto.getUserUpdateId(), plotEntity);

        plotEntity = this.plotRepository.save(plotEntity);
        GetPlotDto getPlotDto = new GetPlotDto();
        this.mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
        return getPlotDto;
    }

    /**
     * Encuentra un lote por su id.
     *
     * @param plotId id del lote a buscar.
     * @throws EntityNotFoundException si el lote no existe.
     * @return el lote encontrado.
     */
    public PlotEntity getPlotEntityById(Integer plotId) {
        return plotRepository.findById(plotId)
                .orElseThrow(() -> new EntityNotFoundException("Plot not found with id: " + plotId));
    }

    /**
     * Actualiza los campos de un lote.
     *
     * @param plotEntity lote a actualizar.
     * @param plotDto datos del lote a actualizar.
     */
    public void updatePlotFields(PlotEntity plotEntity, PutPlotDto plotDto) {
        plotEntity.setTotalAreaInM2(plotDto.getTotal_area_in_m2());
        plotEntity.setBuiltAreaInM2(plotDto.getBuilt_area_in_m2());
        plotEntity.setPlotState(getPlotState(plotDto.getPlot_state_id()));
        plotEntity.setPlotType(getPlotType(plotDto.getPlot_type_id()));
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(plotDto.getUserUpdateId());
        plotEntity.getFiles().clear();
    }

    /**
     * Obtiene todos los lotes.
     *
     * @return una lista con todos los lotes.
     */
    @Override
    public List<GetPlotDto> getAllPlots() {
        List<PlotEntity> plotEntities = plotRepository.findAll();
        List<GetPlotDto> plotDtos = new ArrayList<>();
        for (PlotEntity plotEntity : plotEntities) {
            GetPlotDto getPlotDto = new GetPlotDto();
            mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
            getPlotDto.setFiles(fileService.getPlotFiles(plotEntity.getId()));
            plotDtos.add(getPlotDto);
        }
        return plotDtos;
    }

    /**
     * Obtiene todos los lotes.
     *
     * @return una lista con todos los lotes, y con su owner.
     */
    @Override
    public List<GetPlotWithHisOwnerDto> getPlotsWithHisOwner() {
        List<PlotEntity> plotEntities = plotRepository.findAll();
        List<GetPlotWithHisOwnerDto> plotDtos = new ArrayList<>();

        List<GetPlotOwnerDto> plotOwners = plotOwnerService.getAllPlotOwner();

        // Crear un mapa para acceder fácilmente a los ownerId por plot_id
        Map<Integer, Integer> plotOwnerMap = plotOwners.stream()
                .collect(Collectors.toMap(GetPlotOwnerDto::getPlot_id, GetPlotOwnerDto::getOwner_id));

        for (PlotEntity plotEntity : plotEntities) {
            GetPlotWithHisOwnerDto getPlotDto = new GetPlotWithHisOwnerDto();
            mapPlotEntityToGetPlotWithHisOwnerDto(plotEntity, getPlotDto);
            getPlotDto.setFiles(fileService.getPlotFiles(plotEntity.getId()));

            // Asignar el ownerId correspondiente
            getPlotDto.setOwnerId(plotOwnerMap.get(plotEntity.getId()));

            plotDtos.add(getPlotDto);
        }

        return plotDtos;
    }

    /**
     * Mapea los datos de un lote a un dto de lote con el ID del propietario.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param getPlotDto dto de lote a mapear.
     */
    public void mapPlotEntityToGetPlotWithHisOwnerDto(PlotEntity plotEntity, GetPlotWithHisOwnerDto getPlotDto) {
        getPlotDto.setPlot_number(plotEntity.getPlotNumber());
        getPlotDto.setBlock_number(plotEntity.getBlockNumber());
        getPlotDto.setBuilt_area_in_m2(plotEntity.getBuiltAreaInM2());
        getPlotDto.setTotal_area_in_m2(plotEntity.getTotalAreaInM2());
        getPlotDto.setId(plotEntity.getId());
        getPlotDto.setPlot_state(plotEntity.getPlotState().getName());
        getPlotDto.setPlot_type(plotEntity.getPlotType().getName());
    }

    /**
     * Obtiene todos los lotes disponibles.
     *
     * @return una lista con todos los lotes disponibles.
     */
    @Override
    public List<GetPlotDto> getAllPlotsAvailables() {
        List<PlotEntity> plotEntities = plotRepository.findPlotsAvailables();
        List<GetPlotDto> plotDtos = new ArrayList<>();
        for (PlotEntity plotEntity : plotEntities) {
            GetPlotDto getPlotDto = new GetPlotDto();
            mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
            getPlotDto.setFiles(fileService.getPlotFiles(plotEntity.getId()));
            plotDtos.add(getPlotDto);
        }
        return plotDtos;
    }

    /**
     * Obtiene un lote por su id.
     *
     * @param plotId id del lote a buscar.
     * @throws EntityNotFoundException si el lote no existe.
     * @return el lote encontrado.
     */
    @Override
    public GetPlotDto getPlotById(Integer plotId) {
        PlotEntity plotEntity = plotRepository.findById(plotId)
                .orElseThrow(() -> new EntityNotFoundException("Plot not found with id: " + plotId));
        GetPlotDto getPlotDto = new GetPlotDto();
        mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
        getPlotDto.setFiles(fileService.getPlotFiles(plotEntity.getId()));
        return getPlotDto;
    }

    /**
     * Obtiene los lotes de un propietario.
     *
     * @param ownerdId id del propietario.
     * @return lista de lotes del propietario.
     */
    @Override
    public List<GetPlotDto> getPlotByOwnerId(Integer ownerdId) {
        List<GetPlotDto> plotDtos = new ArrayList<>();
        List<PlotOwnerEntity> plotOwnerEntity = plotOwnerRepository.findByOwnerId(ownerdId);
        for (PlotOwnerEntity plotOwner : plotOwnerEntity) {
            GetPlotDto getPlotDto = new GetPlotDto();
            PlotEntity plotEntity = plotRepository.findById(plotOwner.getPlot().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Plot not found"));
            mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
            plotDtos.add(getPlotDto);
        }
        return plotDtos;
    }

    /**
     * Transfiere un lote de un propietario a otro.
     *
     * @param plotId id del lote a transferir.
     * @param ownerId id del nuevo propietario.
     * @param userId id del usuario que realiza la transferencia.
     */
    @Transactional
    @Override
    public void transferPlot(Integer plotId, Integer ownerId, Integer userId) {
        PlotEntity plotEntity = plotRepository.findById(plotId)
                .orElseThrow(() -> new EntityNotFoundException("Plot not found with id: " + plotId));

        PlotOwnerEntity plotOwnerEntity = plotOwnerRepository.findByPlotId(plotEntity.getId());

        if (plotOwnerRepository.findByPlotId(plotId) != null) {
            plotOwnerService.deletePlotOwner(plotId, plotOwnerEntity.getOwner().getId());
        }
        else {
            changePlotState(plotId, userId); //Cambia el estado a habitado si es un lote que no tiene propietario
        }

        plotOwnerService.createPlotOwner(ownerId, plotId, userId);
    }

    /**
     * Válida si un lote existe con el número de lote pasado.
     *
     * @param plotNumber número de lote a validar.
     * @throws IllegalArgumentException si el lote ya existe.
     */
    public void validatePlotNumber(Integer plotNumber) {
        if (plotRepository.findByPlotNumber(plotNumber) != null) {
            throw new IllegalArgumentException("Error creating plot: Plot already exist.");
        }
    }

    /**
     * Mapea los datos de un lote a una entidad de lote.
     *
     * @param postPlotDto datos del lote a mapear.
     * @return entidad de lote mapeada.
     */
    public PlotEntity mapPlotPostToPlotEntity(PostPlotDto postPlotDto) {
        PlotEntity plotEntity = new PlotEntity();
        mapPlotPostToPlotEntity(plotEntity, postPlotDto);
        plotEntity.setPlotType(getPlotType(postPlotDto.getPlot_type_id()));
        plotEntity.setPlotState(getPlotState(postPlotDto.getPlot_state_id()));
        return plotEntity;
    }

    /**
     * Mapea los datos post dto de un lote a una entidad de lote.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param postPlotDto datos del lote a mapear.
     */
    public void mapPlotPostToPlotEntity(PlotEntity plotEntity, PostPlotDto postPlotDto) {
        plotEntity.setPlotNumber(postPlotDto.getPlot_number());
        plotEntity.setBlockNumber(postPlotDto.getBlock_number());
        plotEntity.setBuiltAreaInM2(postPlotDto.getBuilt_area_in_m2());
        plotEntity.setTotalAreaInM2(postPlotDto.getTotal_area_in_m2());
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setCreatedUser(postPlotDto.getUserCreateId());
        plotEntity.setLastUpdatedUser(postPlotDto.getUserCreateId());
    }

    /**
     * Obtiene un estado de lote por su id.
     *
     * @param id id del estado de lote a buscar.
     * @throws EntityNotFoundException si el estado de lote no existe.
     * @return el estado de lote encontrado.
     */
    public PlotStateEntity getPlotState(Integer id) {
        return plotStateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlotState not found with id: " + id));
    }

    /**
     * Obtiene un tipo de lote por su id.
     *
     * @param id id del tipo de lote a buscar.
     * @throws EntityNotFoundException si el tipo de lote no existe.
     * @return el tipo de lote encontrado.
     */
    public PlotTypeEntity getPlotType(Integer id) {
        return plotTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlotType not found with id: " + id));
    }

    /**
     * Mapea los datos de un lote a un dto de lote.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param getPlotDto dto de lote a mapear.
     */
    @Override
    public void mapPlotEntityToGetPlotDto(PlotEntity plotEntity, GetPlotDto getPlotDto) {
        getPlotDto.setPlot_number(plotEntity.getPlotNumber());
        getPlotDto.setBlock_number(plotEntity.getBlockNumber());
        getPlotDto.setBuilt_area_in_m2(plotEntity.getBuiltAreaInM2());
        getPlotDto.setTotal_area_in_m2(plotEntity.getTotalAreaInM2());
        getPlotDto.setId(plotEntity.getId());
        getPlotDto.setPlot_state(plotEntity.getPlotState().getName());
        getPlotDto.setPlot_type(plotEntity.getPlotType().getName());
    }

}
