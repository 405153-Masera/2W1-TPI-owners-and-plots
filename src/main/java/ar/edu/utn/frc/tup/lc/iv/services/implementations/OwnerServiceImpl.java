package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestUser;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final TaxStatusRepository taxStatusRepository;

    private final OwnerTypeRepository ownerTypeRepository;

    private final PlotOwnerRepository plotOwnerRepository;

    private final ModelMapper modelMapper;

    private final RestUser restUser;
    private final PlotRepository plotRepository;
    private final PlotService plotService;
    private final FileManagerClient fileManagerClient;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, TaxStatusRepository taxStatusRepository,
                            OwnerTypeRepository ownerTypeRepository, PlotOwnerRepository plotOwnerRepository, ModelMapper modelMapper,
                            RestUser restUser, PlotRepository plotRepository, PlotService plotService, FileManagerClient fileManagerClient) {
        this.ownerRepository = ownerRepository;
        this.taxStatusRepository = taxStatusRepository;
        this.ownerTypeRepository = ownerTypeRepository;
        this.plotOwnerRepository = plotOwnerRepository;
        this.modelMapper = modelMapper;
        this.restUser = restUser;
        this.plotRepository = plotRepository;
        this.plotService = plotService;
        this.fileManagerClient = fileManagerClient;
    }

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

        uploadFiles(postOwnerDto.getFiles() , postOwnerDto.getUserCreateId(),ownerEntity);

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);
        //Se guarda la relacion de Owner con Plot
        createPlotOwnerEntity(ownerSaved, postOwnerDto);


        //Aca se crea el usuario
        if(!restUser.createUser(postOwnerDto)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error while creating the user");
        }

        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerTypeEntity.getDescription());
        getOwnerDto.setTaxStatus(taxStatusEntity.getDescription());
        return getOwnerDto;
    }

    public void uploadFiles(List<MultipartFile> files, Integer userId, OwnerEntity ownerEntity) {
        if(files != null && !files.isEmpty()){

            for (MultipartFile file : files) {

                String fileUuid = fileManagerClient.uploadFile(file);

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileUuid(fileUuid);
                fileEntity.setName(file.getName());
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

    //Metodo para gaurdar la relacion de Plot con Owner en las tablas
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

    @Override
    public GetOwnerDto updateOwner(Integer ownerId, PutOwnerDto putOwnerDto) {
        Optional<OwnerEntity> ownerEntityOptional = ownerRepository.findById(ownerId);

        if (ownerEntityOptional.isEmpty()){
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

        //Aca se actualiza el usuario

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);
        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerSaved.getOwnerType().getDescription());
        getOwnerDto.setTaxStatus(ownerSaved.getTaxStatus().getDescription());

        return getOwnerDto;
    }

    @Override
    public GetOwnerDto getById(Integer ownerId) {
        OwnerEntity ownerEntity = ownerRepository.findById(ownerId).orElse(null);
        if(ownerEntity == null){
            throw new EntityNotFoundException("Owner not found");
        }
        return mapOwnerEntitytoGet(ownerEntity);
    }

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

    @Override
    public List<GetTaxStatusDto> getTaxStatus() {
        List<TaxStatusEntity> taxStatusEntities = taxStatusRepository.findAll();
        List<GetTaxStatusDto> taxStatusDtos = new ArrayList<>();
        for (TaxStatusEntity taxStatusEntity : taxStatusEntities) {
            taxStatusDtos.add(modelMapper.map(taxStatusEntity, GetTaxStatusDto.class));
        }
        return taxStatusDtos;
    }

    @Override
    public List<GetOwnerTypeDto> getOwnerTypes() {
        List<OwnerTypeEntity> ownerTypeEntities = ownerTypeRepository.findAll();
        List<GetOwnerTypeDto> ownerTypeDtos = new ArrayList<>();
        for (OwnerTypeEntity ownerTypeEntity : ownerTypeEntities) {
            ownerTypeDtos.add(modelMapper.map(ownerTypeEntity, GetOwnerTypeDto.class));
        }
        return ownerTypeDtos;
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        List<OwnerEntity> ownerEntities = ownerRepository.findAll();
        List<OwnerDto> ownerDtos = ownerEntities.stream()
                .map(this::mapOwnerEntityToOwnerDto)
                .collect(Collectors.toList());

        //Todo: falta agregar los archivos

        return ownerDtos;
    }

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

    @Override
    @Transactional
    public void deleteOwner(Integer ownerId, Integer userIdUpdate) {
        Optional<OwnerEntity> optionalOwner = ownerRepository.findById(ownerId);

        if(optionalOwner.isEmpty()){
            throw new EntityNotFoundException("Owner not found with id: " + ownerId);
        }
        OwnerEntity ownerEntity = optionalOwner.get();
        ownerEntity.setActive(false);
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(userIdUpdate);
        ownerRepository.save(ownerEntity);

        restUser.deleteUser(ownerEntity.getId(), userIdUpdate);
    }

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
