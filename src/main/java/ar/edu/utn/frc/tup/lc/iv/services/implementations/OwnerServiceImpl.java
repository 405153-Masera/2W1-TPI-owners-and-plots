package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.OwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.TaxStatusEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.TaxStatusRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestUser;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    private final ModelMapper modelMapper;

    private final RestUser restUser;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, TaxStatusRepository taxStatusRepository,
                            OwnerTypeRepository ownerTypeRepository, ModelMapper modelMapper,
                            RestUser restUser) {
        this.ownerRepository = ownerRepository;
        this.taxStatusRepository = taxStatusRepository;
        this.ownerTypeRepository = ownerTypeRepository;
        this.modelMapper = modelMapper;
        this.restUser = restUser;
    }

    @Override
    public GetOwnerDto createOwner(PostOwnerDto postOwnerDto) {

        OwnerEntity ownerEntity = mapPostToOwnerEntity(postOwnerDto);

        OwnerTypeEntity ownerTypeEntity = ownerTypeRepository.findById(postOwnerDto.getOwnerTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerType not found"));
        ownerEntity.setOwnerType(ownerTypeEntity);

        TaxStatusEntity taxStatusEntity = taxStatusRepository.findById(postOwnerDto.getTaxStatusId())
                .orElseThrow(() -> new EntityNotFoundException("TaxStatus not found"));
        ownerEntity.setTaxStatus(taxStatusEntity);

        //Aca se crea el usuario
        if(!restUser.createUser(postOwnerDto)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error while creating the user");
        }

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);

        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerTypeEntity.getDescription());
        getOwnerDto.setTaxStatus(taxStatusEntity.getDescription());
        return getOwnerDto;
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
        getOwnerDto.setSurname(ownerEntity.getLastname());
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

        return ownerDtos;
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
