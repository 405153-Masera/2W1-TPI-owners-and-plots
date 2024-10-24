package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestUser;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz OwnerService,
 * contiene toda la logica de propietarios.
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    /**
     * Repositorio para manejar Owner entities.
     */
    @Autowired
    private OwnerRepository ownerRepository;

    /**
     * Repositorio para manejar TaxStatus entities.
     */
    @Autowired
    private TaxStatusRepository taxStatusRepository;

    /**
     * Repositorio para manejar OwnerType entities.
     */
    @Autowired
    private OwnerTypeRepository ownerTypeRepository;

    /**
     * Repositorio para manejar PlotOwner entities.
     */
    @Autowired
    private PlotOwnerRepository plotOwnerRepository;

    /**
     * Mapper para mapear entidades a DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Servicio para manejar la comunicación con el api de usuarios.
     */
    @Autowired
    private RestUser restUser;

    /**
     * Repositorio para manejar Plot entities.
     */
    @Autowired
    private PlotRepository plotRepository;

    /**
     * Servicio para manejar la lógica de lotes.
     */
    @Autowired
    private PlotService plotService;

    /**
     * Servicio para manejar la lógica de archivos.
     */
    @Autowired
    private FileService fileService;

    /**
     * Servicio para manejar la comunicación con el api de archivos.
     */
    @Autowired
    private FileManagerClient fileManagerClient;

    /**
     * Constructor de OwnerServiceImpl.
     *
     * @param ownerRepository el repositorio de propietarios.
     * @param taxStatusRepository el repositorio de estados impositivos.
     * @param ownerTypeRepository el repositorio de tipos de propietarios.
     * @param plotOwnerRepository el repositorio de propietarios de lotes.
     * @param restUser el servicio para manejar la comunicación con el api de usuarios.
     * @param plotRepository el repositorio de lotes.
     * @param plotService el servicio para manejar la lógica de lotes.
     */
//    @Autowired
//    public OwnerServiceImpl(OwnerRepository ownerRepository, TaxStatusRepository taxStatusRepository,
//                            OwnerTypeRepository ownerTypeRepository, PlotOwnerRepository plotOwnerRepository,
//                            RestUser restUser, PlotRepository plotRepository,
//                            PlotService plotService) {
//        this.ownerRepository = ownerRepository;
//        this.taxStatusRepository = taxStatusRepository;
//        this.ownerTypeRepository = ownerTypeRepository;
//        this.plotOwnerRepository = plotOwnerRepository;
//        this.restUser = restUser;
//        this.plotRepository = plotRepository;
//        this.plotService = plotService;
//    }

    /**
     * Crea un propietario y el usuario del propietario.
     *
     * @param postOwnerDto el DTO con la información del propietario y su usuario.
     * @throws EntityNotFoundException si no se encuentra el tipo de propietario o el estado impositivo.
     * @throws ResponseStatusException si ocurre un error al crear el usuario.
     * @return el DTO con la información del propietario creado.
     */
    @Override
    @Transactional
    public GetOwnerDto createOwner(PostOwnerDto postOwnerDto) {

        OwnerEntity ownerEntity = mapPostToOwnerEntity(postOwnerDto);

        OwnerTypeEntity ownerTypeEntity = ownerTypeRepository.findById(postOwnerDto.getOwnerTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerType not found"));
        ownerEntity.setOwnerType(ownerTypeEntity);

        TaxStatusEntity taxStatusEntity = taxStatusRepository.findById(postOwnerDto.getTaxStatusId())
                .orElseThrow(() -> new EntityNotFoundException("TaxStatus not found"));
        ownerEntity.setTaxStatus(taxStatusEntity);

        uploadFiles(postOwnerDto.getFiles(), postOwnerDto.getUserCreateId(), ownerEntity);

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);
        //Se guarda la relacion de Owner con Plot
        createPlotOwnerEntity(ownerSaved, postOwnerDto);


        //Aca se crea el usuario
        if (!restUser.createUser(postOwnerDto)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error while creating the user");
        }

        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerTypeEntity.getDescription());
        getOwnerDto.setTaxStatus(taxStatusEntity.getDescription());
        return getOwnerDto;
    }

    /**
     * Sube los archivos de un propietario.
     *
     * @param files los archivos a subir.
     * @param userId el id del usuario que sube los archivos.
     * @param ownerEntity la entidad del propietario.
     */
    public void uploadFiles(List<MultipartFile> files, Integer userId, OwnerEntity ownerEntity) {
        if (files != null && !files.isEmpty()) {

            for (MultipartFile file : files) {

                String fileUuid = fileManagerClient.uploadFile(file).getUuid().toString();

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileUuid(fileUuid);
                fileEntity.setName(file.getOriginalFilename());
                fileEntity.setCreatedDatetime(LocalDateTime.now());
                fileEntity.setCreatedUser(userId);
                fileEntity.setLastUpdatedDatetime(LocalDateTime.now());
                fileEntity.setLastUpdatedUser(userId);

                FileOwnerEntity fileOwnerEntity = new FileOwnerEntity();
                fileOwnerEntity.setFile(fileEntity);
                fileOwnerEntity.setOwner(ownerEntity);
                fileOwnerEntity.setCreatedDatetime(LocalDateTime.now());
                fileOwnerEntity.setCreatedUser(userId);
                fileOwnerEntity.setLastUpdatedDatetime(LocalDateTime.now());
                fileOwnerEntity.setLastUpdatedUser(userId);

                ownerEntity.getFiles().add(fileOwnerEntity);
            }
        }
    }

    /**
     * Crea la relación entre un propietario y un lote.
     *
     * @param ownerEntity  la entidad del propietario.
     * @param postOwnerDto el DTO con la información del propietario.
     */
    public void createPlotOwnerEntity(OwnerEntity ownerEntity, PostOwnerDto postOwnerDto) {
        PlotOwnerEntity plotOwnerEntity = new PlotOwnerEntity();
        plotOwnerEntity.setOwner(ownerEntity);
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(postOwnerDto.getPlotId());
        plotOwnerEntity.setPlot(plotEntity);
        plotOwnerEntity.setCreatedUser(postOwnerDto.getUserCreateId());
        plotOwnerEntity.setCreatedDatetime(LocalDateTime.now());
        plotOwnerEntity.setLastUpdatedUser(postOwnerDto.getUserCreateId());
        plotOwnerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotOwnerRepository.save(plotOwnerEntity);
    }

    /**
     * Actualiza un propietario.
     *
     * @param ownerId el id del propietario a actualizar.
     * @param putOwnerDto el DTO con la información del propietario a actualizar.
     * @throws EntityNotFoundException si no se encuentra el propietario, el tipo de propietario o el estado impositivo.
     * @return el DTO con la información del propietario actualizado.
     */
    @Override
    public GetOwnerDto updateOwner(Integer ownerId, PutOwnerDto putOwnerDto) {
        Optional<OwnerEntity> ownerEntityOptional = ownerRepository.findById(ownerId);

        if (ownerEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Owner not found");
        }
        OwnerEntity ownerEntity = ownerEntityOptional.get();
        ownerEntity.setName(putOwnerDto.getName());
        ownerEntity.setLastname(putOwnerDto.getLastname());
        ownerEntity.setDni(putOwnerDto.getDni());
        ownerEntity.setCuitCuil(putOwnerDto.getCuitCuil());
        ownerEntity.setDateBirth(putOwnerDto.getDateBirth());
        ownerEntity.setOwnerType(ownerTypeRepository.findById(putOwnerDto.getOwnerTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerType not found")));
        ownerEntity.setTaxStatus(taxStatusRepository.findById(putOwnerDto.getTaxStatusId())
                .orElseThrow(() -> new EntityNotFoundException("TaxStatus not found")));
        ownerEntity.setBusinessName(putOwnerDto.getBusinessName());
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(putOwnerDto.getUserUpdateId());

        uploadFiles(putOwnerDto.getFiles(), putOwnerDto.getUserUpdateId(), ownerEntity);

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);
        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerSaved.getOwnerType().getDescription());
        getOwnerDto.setTaxStatus(ownerSaved.getTaxStatus().getDescription());

        return getOwnerDto;
    }

    /**
     * Obtiene un propietario por su id.
     *
     * @param ownerId el id del propietario a buscar.
     * @throws EntityNotFoundException si no se encuentra el propietario.
     * @return el DTO con la información del propietario.
     */
    @Override
    public GetOwnerDto getById(Integer ownerId) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElseThrow(() ->
                new EntityNotFoundException("Owner not found"));

        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerEntity);
        getOwnerDto.setFiles(fileService.getOwnerFiles(ownerId));
        return getOwnerDto;
    }

    /**
     * Mapea el DTO de entrada a una entidad Owner.
     *
     * @param postOwnerDto el DTO de entrada con la información del propietario.
     * @return la entidad Owner correspondiente.
     */
    public OwnerEntity mapPostToOwnerEntity(PostOwnerDto postOwnerDto) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setName(postOwnerDto.getName());
        ownerEntity.setLastname(postOwnerDto.getLastname());
        ownerEntity.setDni(postOwnerDto.getDni());
        ownerEntity.setCuitCuil(postOwnerDto.getCuitCuil());
        ownerEntity.setDateBirth(postOwnerDto.getDateBirth());
        ownerEntity.setBusinessName(postOwnerDto.getBusinessName());
        ownerEntity.setActive(postOwnerDto.getActive());
        ownerEntity.setCreatedDatetime(LocalDateTime.now());
        ownerEntity.setCreatedUser(postOwnerDto.getUserCreateId());
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(postOwnerDto.getUserCreateId());
        return ownerEntity;
    }

    /**
     * Mapea una entidad Owner a un DTO de salida.
     *
     * @param ownerEntity la entidad Owner a mapear.
     * @return  el DTO de salida con la información del propietario.
     */
    public GetOwnerDto mapOwnerEntitytoGet(OwnerEntity ownerEntity) {
        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(ownerEntity.getId());
        getOwnerDto.setName(ownerEntity.getName());
        getOwnerDto.setLastname(ownerEntity.getLastname());
        getOwnerDto.setDni(ownerEntity.getDni());
        getOwnerDto.setCuitCuil(ownerEntity.getCuitCuil());
        getOwnerDto.setDateBirth(ownerEntity.getDateBirth());
        getOwnerDto.setOwnerType(ownerEntity.getOwnerType().getDescription());
        getOwnerDto.setTaxStatus(ownerEntity.getTaxStatus().getDescription());
        getOwnerDto.setBusinessName(ownerEntity.getBusinessName());
        getOwnerDto.setActive(ownerEntity.getActive());
        return getOwnerDto;
    }

    /**
     * Obtiene los tipos de situación fiscal.
     *
     * @return una lista de los tipos de situación fiscal.
     */
    @Override
    public List<GetTaxStatusDto> getTaxStatus() {
        List<TaxStatusEntity> taxStatusEntities = taxStatusRepository.findAll();
        List<GetTaxStatusDto> taxStatusDtos = new ArrayList<>();
        for (TaxStatusEntity taxStatusEntity : taxStatusEntities) {
            taxStatusDtos.add(modelMapper.map(taxStatusEntity, GetTaxStatusDto.class));
        }
        return taxStatusDtos;
    }

    /**
     * Obtiene todos los tipos de propietarios (personas física, jurídica, otros).
     *
     * @return una lista con los tipos de propietarios.
     */
    @Override
    public List<GetOwnerTypeDto> getOwnerTypes() {
        List<OwnerTypeEntity> ownerTypeEntities = ownerTypeRepository.findAll();
        List<GetOwnerTypeDto> ownerTypeDtos = new ArrayList<>();
        for (OwnerTypeEntity ownerTypeEntity : ownerTypeEntities) {
            ownerTypeDtos.add(modelMapper.map(ownerTypeEntity, GetOwnerTypeDto.class));
        }
        return ownerTypeDtos;
    }


    /**
     * Obtiene todos los propietarios.
     *
     * @return una lista con todos los propietarios.
     */
    @Override
    public List<GetOwnerDto> getAllOwners() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAll();
        List<GetOwnerDto> ownerDtos = new ArrayList<>();

        for (OwnerEntity ownerEntity : ownerEntities) {
            GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerEntity);
            getOwnerDto.setFiles(fileService.getOwnerFiles(ownerEntity.getId()));
            ownerDtos.add(getOwnerDto);
        }
        return ownerDtos;
    }

    /**
     * Obtiene todos los propietarios activos, junto con su lote y su usuario.
     *
     * @return una lista con todos los propietarios activos, su lote y su usuario.
     */
    @Override
    public List<GetOwnerAndPlot> getOwersAndPlots() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAllActives();
        List<GetOwnerAndPlot> ownerAndPlots = new ArrayList<>();
        for (OwnerEntity ownerEntity : ownerEntities) {
            GetOwnerAndPlot getOwnerAndPlot = new GetOwnerAndPlot();
            PlotOwnerEntity plotOwnerEntity = plotOwnerRepository.findByOwnerId(ownerEntity.getId());
            PlotEntity plotEntity = plotRepository.findById(plotOwnerEntity.getPlot().getId()).orElse(null);

            OwnerDto ownerDto = mapOwnerEntityToOwnerDto(ownerEntity);
            GetPlotDto getPlotDto = new GetPlotDto();
            plotService.mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
            GetUserDto getUserDto = restUser.getUser(getPlotDto.getId());

            getOwnerAndPlot.setOwner(ownerDto);
            getOwnerAndPlot.setPlot(getPlotDto);
            getOwnerAndPlot.setUser(getUserDto);

            ownerAndPlots.add(getOwnerAndPlot);
        }
        return ownerAndPlots;
    }

    /**
     * Obtiene un propietario por su id, junto con su lote y su usuario.
     *
     * @param ownerId el id del propietario a buscar.
     * @return el propietario, su lote y su usuario.
     */
    @Override
    public GetOwnerAndPlot getOwnerAndPlotById(Integer ownerId) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElse(null);
        if (ownerEntity == null) {
            return null;
        }

        GetOwnerAndPlot getOwnerAndPlot = new GetOwnerAndPlot();
        PlotOwnerEntity plotOwnerEntity = plotOwnerRepository.findByOwnerId(ownerEntity.getId());
        PlotEntity plotEntity = plotRepository.findById(plotOwnerEntity.getPlot().getId()).orElse(null);

        OwnerDto ownerDto = mapOwnerEntityToOwnerDto(ownerEntity);
        GetPlotDto getPlotDto = new GetPlotDto();
        plotService.mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
        GetUserDto getUserDto = restUser.getUser(getPlotDto.getId());

        getOwnerAndPlot.setOwner(ownerDto);
        getOwnerAndPlot.setPlot(getPlotDto);
        getOwnerAndPlot.setUser(getUserDto);

        return getOwnerAndPlot;
    }

    /**
     * Obtiene los propietarios de un lote.
     *
     * @param plotId el id del lote a buscar.
     * @return una lista con los propietarios del lote.
     */
    @Override
    public List<OwnerDto> getOwnersByPlotId(Integer plotId) {
        Optional<List<OwnerEntity>> ownerEntitiesOptional = ownerRepository.findByPlotId(plotId);

        if (ownerEntitiesOptional.isEmpty()) {
            throw new EntityNotFoundException("Owners not found");
        }

        List<OwnerEntity> ownerEntities = ownerEntitiesOptional.get();
        List<OwnerDto> ownerDtos = ownerEntities.stream()
                .map(this::mapOwnerEntityToOwnerDto)
                .collect(Collectors.toList());

        //Todo: falta agregar los archivos

        return ownerDtos;
    }

    /**
     * Baja lógica de un propietario y su usuario.
     *
     * @param ownerId el id del propietario a dar de baja.
     * @param userIdUpdate el id del usuario que da de baja al propietario.
     * @throws EntityNotFoundException si no se encuentra el propietario
     */
    @Override
    @Transactional
    public void deleteOwner(Integer ownerId, Integer userIdUpdate) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElseThrow(()->
                new EntityNotFoundException("Owner not found with id: " + ownerId)
        );

        ownerEntity.setActive(false);
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(userIdUpdate);
        ownerRepository.save(ownerEntity);

        restUser.deleteUser(ownerEntity.getId(), userIdUpdate);
    }

    /**
     * Mapea una entidad Owner a un DTO de salida.
     *
     * @param ownerEntity la entidad Owner a mapear.
     * @return  el DTO de salida con la información del propietario.
     */
    public OwnerDto mapOwnerEntityToOwnerDto(OwnerEntity ownerEntity) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(ownerEntity.getId());
        ownerDto.setName(ownerEntity.getName());
        ownerDto.setLastname(ownerEntity.getLastname());
        ownerDto.setDni(ownerEntity.getDni());
        ownerDto.setCuitCuil(ownerEntity.getCuitCuil());
        ownerDto.setDateBirth(ownerEntity.getDateBirth());
        ownerDto.setOwnerType(ownerEntity.getOwnerType().getDescription());
        ownerDto.setBusinessName(ownerEntity.getBusinessName());
        ownerDto.setActive(ownerEntity.getActive());
        return ownerDto;
    }
}
