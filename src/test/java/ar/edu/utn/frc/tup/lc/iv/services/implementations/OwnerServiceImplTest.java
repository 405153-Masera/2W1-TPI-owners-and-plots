package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.TaxStatusEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.TaxStatusRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestUser;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OwnerServiceImplTest {
    @MockBean
    private RestUser restUserMock;

    @MockBean
    private TaxStatusRepository taxStatusRepositoryMock;

    @MockBean
    private FileService fileServiceMock;

    @MockBean
    private OwnerRepository ownerRepositoryMock;

    @SpyBean
    private OwnerService ownerServiceSpy;

    @Test
    void updateOwner() {
    }

    @Test
    void getById_Success() {
        //Given
        OwnerTypeEntity ownerTypeEntity  = new OwnerTypeEntity();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(12);
        ownerEntity.setName("Bruno");
        ownerEntity.setLastname("Diaz");
        ownerEntity.setOwnerType(ownerTypeEntity);
        ownerEntity.setTaxStatus(taxStatusEntity);

        //When
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto("foto de perfil", "123456789"));

        Mockito.when(ownerRepositoryMock.findById(12)).thenReturn(Optional.of(ownerEntity));
        Mockito.when(fileServiceMock.getOwnerFiles(12)).thenReturn(fileDtoList);

        //Then
        GetOwnerDto result = ownerServiceSpy.getById(12);

        Mockito.verify(ownerRepositoryMock, Mockito.times(1)).findById(12);
        Mockito.verify(fileServiceMock, Mockito.times(1)).getOwnerFiles(12);

        assertEquals(ownerEntity.getId(), result.getId());
        assertEquals(ownerEntity.getName(), result.getName());
        assertNotNull(result.getFiles());
    }

    @Test
    void getById_EntityNotFound() {
        Mockito.when(ownerRepositoryMock.findById(10)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ownerServiceSpy.getById(10));
    }

    @Test
    void getTaxStatus() {
        //Given
        List<TaxStatusEntity> taxStatusEntities = new ArrayList<>();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();
        taxStatusEntity.setId(1);
        taxStatusEntity.setDescription("asdf");

        taxStatusEntities.add(taxStatusEntity);
        taxStatusEntities.add(taxStatusEntity);
        taxStatusEntities.add(taxStatusEntity);

        //When
        Mockito.when(taxStatusRepositoryMock.findAll()).thenReturn(taxStatusEntities);

        //Then
        List<GetTaxStatusDto> response = ownerServiceSpy.getTaxStatus();
        assertEquals(taxStatusEntities.size(), response.size());
        assertEquals(taxStatusEntities.get(0).getDescription(), response.get(0).getDescription());
        Mockito.verify(taxStatusRepositoryMock, Mockito.times(1)).findAll();
    }


    @Test
    void getOwnerTypes() {
    }

    @Test
    void getAllOwners() {
        //Given
        OwnerTypeEntity ownerTypeEntity  = new OwnerTypeEntity();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto("foto de perfil", "123456789"));

        List<OwnerEntity> ownerEntities = new ArrayList<>();

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(1);
        ownerEntity.setName("Manolo");
        ownerEntity.setTaxStatus(taxStatusEntity);
        ownerEntity.setOwnerType(ownerTypeEntity);

        ownerEntities.add(ownerEntity);
        ownerEntities.add(ownerEntity);
        ownerEntities.add(ownerEntity);

        //When
        Mockito.when(ownerRepositoryMock.findAll()).thenReturn(ownerEntities);
        Mockito.when(fileServiceMock.getOwnerFiles(1)).thenReturn(fileDtoList);

        //Then
        List<GetOwnerDto> result = ownerServiceSpy.getAllOwners();

        assertEquals(ownerEntities.size(), result.size());
        assertEquals(ownerEntities.get(0).getName(), result.get(0).getName());

        Mockito.verify(ownerRepositoryMock, Mockito.times(1)).findAll();
        Mockito.verify(fileServiceMock, Mockito.times(3)).getOwnerFiles(1);
    }

    @Test
    void getOwersAndPlots() {
    }

    @Test
    void getOwnerAndPlotById() {
    }

    @Test
    void getOwnersByPlotId() {
    }

    @Test
    void deleteOwner_Success() {
        //Given
        OwnerTypeEntity ownerTypeEntity  = new OwnerTypeEntity();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(12);
        ownerEntity.setName("Bruno");
        ownerEntity.setLastname("Diaz");
        ownerEntity.setOwnerType(ownerTypeEntity);
        ownerEntity.setTaxStatus(taxStatusEntity);

        //When
        Mockito.when(ownerRepositoryMock.findById(12)).thenReturn(Optional.of(ownerEntity));

        //Then
        ownerServiceSpy.deleteOwner(12, 1);

        Mockito.verify(ownerRepositoryMock, Mockito.times(1)).save(ownerEntity);
        Mockito.verify(restUserMock, Mockito.times(1)).deleteUser(12, 1);
    }

    @Test
    void deleteOwner_EntityNotFound() {
        Mockito.when(ownerRepositoryMock.findById(12)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ownerServiceSpy.deleteOwner(12, 1));
        Mockito.verify(ownerRepositoryMock, Mockito.times(1)).findById(12);
        Mockito.verify(ownerRepositoryMock, Mockito.times(0)).save(Mockito.any(OwnerEntity.class));
        Mockito.verify(restUserMock, Mockito.times(0)).deleteUser(12, 1);
    }
}