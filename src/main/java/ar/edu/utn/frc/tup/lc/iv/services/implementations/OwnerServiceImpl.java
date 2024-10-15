package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.TaxStatusEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.TaxStatusRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private TaxStatusRepository taxStatusRepository;

    @Autowired
    private OwnerTypeRepository ownerTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GetOwnerDto createOwner(PostOwnerDto postOwnerDto) {

        OwnerEntity ownerEntity = mapPostToOwnerEntity(postOwnerDto);

        OwnerTypeEntity ownerTypeEntity = ownerTypeRepository.findById(postOwnerDto.getOwnerTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerType not found"));
        ownerEntity.setOwnerType(ownerTypeEntity);

        TaxStatusEntity taxStatusEntity = taxStatusRepository.findById(postOwnerDto.getTaxStatusId())
                .orElseThrow(() -> new EntityNotFoundException("TaxStatus not found"));
        ownerEntity.setTaxStatus(taxStatusEntity);

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);

        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerTypeEntity.getDescription());
        getOwnerDto.setTaxStatus(taxStatusEntity.getDescription());
        //Todo: Guardar el usuario propietario

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
        ownerEntity.setSurname(putOwnerDto.getSurname());
        ownerEntity.setDni(putOwnerDto.getDni());
        ownerEntity.setCuitCuil(putOwnerDto.getCuitCuil());
        ownerEntity.setDateBirth(putOwnerDto.getDateBirth());
        ownerEntity.setOwnerType(ownerTypeRepository.findById(putOwnerDto.getOwnerTypeId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerType not found")));
        ownerEntity.setTaxStatus(taxStatusRepository.findById(putOwnerDto.getTaxStatusId())
                .orElseThrow(() -> new EntityNotFoundException("TaxStatus not found")));
        ownerEntity.setBusinessName(putOwnerDto.getBusinessName());
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(putOwnerDto.getUserId());

        OwnerEntity ownerSaved = ownerRepository.save(ownerEntity);
        GetOwnerDto getOwnerDto = mapOwnerEntitytoGet(ownerSaved);
        getOwnerDto.setOwnerType(ownerSaved.getOwnerType().getDescription());
        getOwnerDto.setTaxStatus(ownerSaved.getTaxStatus().getDescription());

        return getOwnerDto;
    }

    public OwnerEntity mapPostToOwnerEntity(PostOwnerDto postOwnerDto) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setName(postOwnerDto.getName());
        ownerEntity.setSurname(postOwnerDto.getSurname());
        ownerEntity.setDni(postOwnerDto.getDni());
        ownerEntity.setCuitCuil(postOwnerDto.getCuitCuil());
        ownerEntity.setDateBirth(postOwnerDto.getDateBirth());
        ownerEntity.setBusinessName(postOwnerDto.getBusinessName());
        ownerEntity.setActive(postOwnerDto.getActive());

        ownerEntity.setCreatedDatetime(LocalDateTime.now());
        ownerEntity.setCreatedUser(postOwnerDto.getUserId());
        ownerEntity.setLastUpdatedDatetime(LocalDateTime.now());
        ownerEntity.setLastUpdatedUser(postOwnerDto.getUserId());
        return ownerEntity;
    }

    public GetOwnerDto mapOwnerEntitytoGet(OwnerEntity ownerEntity) {
        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(ownerEntity.getId());
        getOwnerDto.setName(ownerEntity.getName());
        getOwnerDto.setSurname(ownerEntity.getSurname());
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
}
