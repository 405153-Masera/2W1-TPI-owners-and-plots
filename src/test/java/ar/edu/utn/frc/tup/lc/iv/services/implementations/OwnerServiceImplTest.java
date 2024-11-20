package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.helpers.OwnerTestHelper;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileClient;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.RestUser;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static ar.edu.utn.frc.tup.lc.iv.helpers.OwnerTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @Mock
    private RestUser restUserMock;

    @Mock
    private PlotService plotService;

    @Mock
    private PlotRepository plotRepositoryMock;

    @Mock
    private TaxStatusRepository taxStatusRepositoryMock;

    @Mock
    private FileService fileServiceMock;

    @Mock
    private OwnerTypeRepository ownerTypeRepositoryMock;

    @Mock
    private OwnerRepository ownerRepositoryMock;

    @Mock
    private PlotOwnerRepository plotOwnerRepositoryMock;

    @Mock
    private PlotStateRepository plotStateRepositoryMock;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private DniTypeRepository dniTypeRepository;

    @Mock
    private FileManagerClient fileManagerClient;

    @InjectMocks
    private OwnerServiceImpl ownerServiceSpy;

    private OwnerEntity testOwnerEntity;
    private PostOwnerDto testPostOwnerDto;
    private PutOwnerDto testPutOwnerDto;
    private DniTypeEntity testDniType;
    private OwnerTypeEntity testOwnerType;
    private TaxStatusEntity testTaxStatus;
    private PlotEntity testPlot;

    @BeforeEach
    void setUp() {
        testDniType = new DniTypeEntity();
        testDniType.setId(1);
        testDniType.setDescription("DNI");

        testOwnerType = new OwnerTypeEntity();
        testOwnerType.setId(1);
        testOwnerType.setDescription("Physical");

        testTaxStatus = new TaxStatusEntity();
        testTaxStatus.setId(1);
        testTaxStatus.setDescription("Active");

        testPlot = new PlotEntity();
        testPlot.setId(1);

        testOwnerEntity = new OwnerEntity();
        testOwnerEntity.setId(1);
        testOwnerEntity.setName("John");
        testOwnerEntity.setLastname("Doe");
        testOwnerEntity.setDni("12345678");
        testOwnerEntity.setDni_type_id(testDniType);
        testOwnerEntity.setDateBirth(LocalDate.of(1990, 1, 1));
        testOwnerEntity.setOwnerType(testOwnerType);
        testOwnerEntity.setTaxStatus(testTaxStatus);
        testOwnerEntity.setBusinessName("Business");
        testOwnerEntity.setActive(true);
        testOwnerEntity.setCreatedDatetime(LocalDateTime.now());
        testOwnerEntity.setLastUpdatedDatetime(LocalDateTime.now());

        testPostOwnerDto = new PostOwnerDto();
        testPostOwnerDto.setName("John");
        testPostOwnerDto.setLastname("Doe");
        testPostOwnerDto.setDni("12345678");
        testPostOwnerDto.setDni_type_id(testDniType.getId());
        testPostOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
        testPostOwnerDto.setOwnerTypeId(1);
        testPostOwnerDto.setTaxStatusId(1);
        testPostOwnerDto.setBusinessName("Business");
        testPostOwnerDto.setActive(true);
        testPostOwnerDto.setUserCreateId(1);
        testPostOwnerDto.setPlotId(new Integer[]{1});

        testPutOwnerDto = new PutOwnerDto();
        testPutOwnerDto.setName("John Updated");
        testPutOwnerDto.setLastname("Doe Updated");
        testPutOwnerDto.setDni("87654321");
        testPutOwnerDto.setDniTypeId(testDniType.getId());
        testPutOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
        testPutOwnerDto.setOwnerTypeId(1);
        testPutOwnerDto.setTaxStatusId(1);
        testPutOwnerDto.setBusinessName("Business Updated");
        testPutOwnerDto.setUserUpdateId(1);
        testPutOwnerDto.setPlotId(new Integer[]{1});
    }


    @Test
    void createOwner_Success() {
        when(ownerTypeRepositoryMock.findById(1)).thenReturn(Optional.of(testOwnerType));
        Integer expectedDniTypeId = testPostOwnerDto.getDni_type_id();
        when(dniTypeRepository.findById(expectedDniTypeId))
                .thenReturn(Optional.of(testDniType));when(taxStatusRepositoryMock.findById(1)).thenReturn(Optional.of(testTaxStatus));
        when(plotRepositoryMock.findById(1)).thenReturn(Optional.of(testPlot));
        when(ownerRepositoryMock.save(any(OwnerEntity.class))).thenReturn(testOwnerEntity);
        when(restUserMock.createUser(any(PostOwnerDto.class))).thenReturn(true);
        when(plotRepositoryMock.findById(1)).thenReturn(Optional.of(testPlot));
        when(plotRepositoryMock.existsById(1)).thenReturn(true);

        GetOwnerDto result = ownerServiceSpy.createOwner(testPostOwnerDto);

        verify(dniTypeRepository).findById(expectedDniTypeId);
        assertNotNull(result);
        assertEquals(testOwnerEntity.getName(), result.getName());
        assertEquals(testOwnerEntity.getLastname(), result.getLastname());
        verify(restUserMock).createUser(any(PostOwnerDto.class));
    }


    @Test
    void updateOwner_Success() {
        when(ownerRepositoryMock.findById(1)).thenReturn(Optional.of(testOwnerEntity));
        Integer expectedDniTypeId = testPutOwnerDto.getDniTypeId();
        when(dniTypeRepository.findById(expectedDniTypeId)).thenReturn(Optional.of(testDniType));
        when(ownerRepositoryMock.save(any(OwnerEntity.class))).thenReturn(testOwnerEntity);
        when(ownerTypeRepositoryMock.findById(1)).thenReturn(Optional.of(testOwnerType));
        when(taxStatusRepositoryMock.findById(1)).thenReturn(Optional.of(testTaxStatus));
        when(plotRepositoryMock.findById(1)).thenReturn(Optional.of(testPlot));
        when(plotRepositoryMock.existsById(1)).thenReturn(true);

        when(restUserMock.updateUser(any(PutOwnerDto.class))).thenReturn(true);

        GetOwnerDto result = ownerServiceSpy.updateOwner(1, testPutOwnerDto);

        verify(dniTypeRepository).findById(expectedDniTypeId);
        assertNotNull(result);
        assertEquals(testOwnerEntity.getName(), result.getName());
        assertEquals(testOwnerEntity.getLastname(), result.getLastname());
        assertEquals(testOwnerEntity.getDni(), result.getDni());
    }


    @Test
    void updateOwner_OwnerEntityNotFoundException() {
        //When
        Mockito.when(ownerRepositoryMock.findById(10)).thenReturn(Optional.empty());

        //Then
        assertThrows(EntityNotFoundException.class, () -> ownerServiceSpy.updateOwner(10, new PutOwnerDto()));
        Mockito.verify(ownerRepositoryMock, Mockito.times(0)).findById(1);
        Mockito.verify(taxStatusRepositoryMock, Mockito.times(0)).findById(1);
        Mockito.verify(ownerRepositoryMock, Mockito.times(0)).save(new OwnerEntity());
    }


    @Test
    void getById_Success() {
        // Given
        OwnerTypeEntity ownerTypeEntity = new OwnerTypeEntity();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(12);
        ownerEntity.setName("Bruno");
        ownerEntity.setLastname("Diaz");
        ownerEntity.setDni_type_id(OwnerTestHelper.DNI_TYPE_ENTITY_PASAPORTE);
        ownerEntity.setOwnerType(ownerTypeEntity);
        ownerEntity.setTaxStatus(taxStatusEntity);
        ownerEntity.setCreatedDatetime(LocalDateTime.now()); // Inicializa el campo aquí

        // When
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto("foto de perfil", "123456789"));

        when(ownerRepositoryMock.findById(12)).thenReturn(Optional.of(ownerEntity));
        when(fileServiceMock.getOwnerFiles(12)).thenReturn(fileDtoList);

        // Then
        GetOwnerDto result = ownerServiceSpy.getById(12);

        verify(ownerRepositoryMock, times(1)).findById(12);
        verify(fileServiceMock, times(1)).getOwnerFiles(12);

        assertEquals(ownerEntity.getId(), result.getId());
        assertEquals(ownerEntity.getName(), result.getName());
        assertNotNull(result.getFiles());
    }

    @Test
    void getById_EntityNotFound() {
        when(ownerRepositoryMock.findById(10)).thenReturn(Optional.empty());

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
        when(taxStatusRepositoryMock.findAll()).thenReturn(taxStatusEntities);

        //Then
        List<GetTaxStatusDto> response = ownerServiceSpy.getTaxStatus();
        assertEquals(taxStatusEntities.size(), response.size());
        assertEquals(taxStatusEntities.get(0).getDescription(), response.get(0).getDescription());
        verify(taxStatusRepositoryMock, times(1)).findAll();
    }


    @Test
    void getOwnerTypes() {
        //Given
        List<OwnerTypeEntity> ownerTypeEntities = new ArrayList<>();

        OwnerTypeEntity ownerTypeEntity = new OwnerTypeEntity();
        ownerTypeEntity.setId(10);
        ownerTypeEntity.setDescription("Judirico");

        ownerTypeEntities.add(ownerTypeEntity);
        ownerTypeEntities.add(ownerTypeEntity);

        //When
        Mockito.when(ownerTypeRepositoryMock.findAll()).thenReturn(ownerTypeEntities);

        //Then
        List<GetOwnerTypeDto> result = ownerServiceSpy.getOwnerTypes();

        assertNotNull(result);
        assertEquals(ownerTypeEntities.size(), result.size());
        assertEquals("Judirico", result.get(0).getDescription());
    }

    @Test
    void getAllOwners() {
        // Given
        OwnerTypeEntity ownerTypeEntity = new OwnerTypeEntity();
        TaxStatusEntity taxStatusEntity = new TaxStatusEntity();
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto("foto de perfil", "123456789"));

        List<OwnerEntity> ownerEntities = new ArrayList<>();

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(1);
        ownerEntity.setName("Manolo");
        ownerEntity.setDni_type_id(OwnerTestHelper.DNI_TYPE_ENTITY_PASAPORTE);
        ownerEntity.setTaxStatus(taxStatusEntity);
        ownerEntity.setOwnerType(ownerTypeEntity);
        ownerEntity.setCreatedDatetime(LocalDateTime.now()); // Inicializa el campo aquí

        ownerEntities.add(ownerEntity);
        ownerEntities.add(ownerEntity);
        ownerEntities.add(ownerEntity);

        // When
        when(ownerRepositoryMock.findAll()).thenReturn(ownerEntities);
        when(fileServiceMock.getOwnerFiles(1)).thenReturn(fileDtoList);

        // Then
        List<GetOwnerDto> result = ownerServiceSpy.getAllOwners();

        assertEquals(ownerEntities.size(), result.size());
        assertEquals(ownerEntities.get(0).getName(), result.get(0).getName());

        verify(ownerRepositoryMock, times(1)).findAll();
        verify(fileServiceMock, times(3)).getOwnerFiles(1);
    }

    @Test
    void getOwersAndPlots() {
        when(ownerRepositoryMock.findAllActives()).thenReturn(OwnerTestHelper.OWNER_ENTITY_LIST);

        when(plotOwnerRepositoryMock.findByOwnerId(1)).thenReturn(List.of(PLOT_OWNER_ENTITY_1));
        when(plotOwnerRepositoryMock.findByOwnerId(2)).thenReturn(List.of(OwnerTestHelper.PLOT_OWNER_ENTITY_2));
        when(plotOwnerRepositoryMock.findByOwnerId(3)).thenReturn(List.of(OwnerTestHelper.PLOT_OWNER_ENTITY_3));

        when(plotRepositoryMock.findById(OwnerTestHelper.PLOT_ENTITY_1.getId())).thenReturn(Optional.of(OwnerTestHelper.PLOT_ENTITY_1));
        when(plotRepositoryMock.findById(OwnerTestHelper.PLOT_ENTITY_2.getId())).thenReturn(Optional.of(OwnerTestHelper.PLOT_ENTITY_2));
        when(plotRepositoryMock.findById(OwnerTestHelper.PLOT_ENTITY_3.getId())).thenReturn(Optional.of(OwnerTestHelper.PLOT_ENTITY_3));

        when(restUserMock.getUser(any())).thenReturn(OwnerTestHelper.GET_USER_DTO);

        List<GetOwnerAndPlot> result = ownerServiceSpy.getOwersAndPlots();

        assertNotNull(result);
        assertEquals(3, result.size());

        GetOwnerAndPlot firstOwner = result.get(0);
        assertEquals("Manu", firstOwner.getOwner().getName());
        assertEquals(1, firstOwner.getPlot().size());
        assertEquals(OwnerTestHelper.GET_USER_DTO.getId(), firstOwner.getUser().getId());
    }

    @Test
    void getOwersAndPlotsException() {
        when(ownerRepositoryMock.findAllActives()).thenReturn(OwnerTestHelper.OWNER_ENTITY_LIST);

        List<PlotOwnerEntity> plotOwnerEntities = List.of(PLOT_OWNER_ENTITY_1);
        when(plotOwnerRepositoryMock.findByOwnerId(1)).thenReturn(plotOwnerEntities);

        when(plotRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ownerServiceSpy.getOwersAndPlots();
        });
        assertNotNull(exception);
        assertEquals("Plot not found for PlotOwner", exception.getMessage());
    }

    @Test
    void getOwnerAndPlotById_Success() {
        Integer ownerId = 1;
        OwnerEntity ownerEntity = OWNER_ENTITY_1;
        PlotOwnerEntity plotOwnerEntity = PLOT_OWNER_ENTITY_1;
        PlotEntity plotEntity = OwnerTestHelper.PLOT_ENTITY_1;
        GetPlotDto getPlotDto = new GetPlotDto();

        when(ownerRepositoryMock.findById(ownerId)).thenReturn(Optional.of(ownerEntity));
        when(plotOwnerRepositoryMock.findByOwnerId(ownerEntity.getId())).thenReturn(Collections.singletonList(plotOwnerEntity));
        when(plotRepositoryMock.findById(plotOwnerEntity.getPlot().getId())).thenReturn(Optional.of(plotEntity));
        doReturn(OwnerTestHelper.GET_USER_DTO).when(restUserMock).getUser(anyInt());

        GetOwnerAndPlot result = ownerServiceSpy.getOwnerAndPlotById(ownerId);

        assertNotNull(result);
        assertEquals(OwnerTestHelper.OWNER_DTO.getName(), result.getOwner().getName());
        assertEquals(Collections.singletonList(getPlotDto), result.getPlot());
        assertEquals(OwnerTestHelper.GET_USER_DTO, result.getUser());
    }

    @Test
    void getOwnerAndPlotById_Null(){
        //When
        Mockito.when(ownerRepositoryMock.findById(10)).thenReturn(Optional.empty());

        //Then
        GetOwnerAndPlot result = ownerServiceSpy.getOwnerAndPlotById(10);

        assertNull(result);
        Mockito.verify(plotOwnerRepositoryMock, Mockito.times(0)).findByOwnerId(anyInt());
        Mockito.verify(plotRepositoryMock, Mockito.times(0)).findById(anyInt());
        Mockito.verify(restUserMock, Mockito.times(0)).getUser(anyInt());
    }

    @Test
    void getOwnersByPlotId_EntityNotFound() {
        //When
        Mockito.when(ownerRepositoryMock.findByPlotId(10)).thenReturn(Optional.empty());

        //Then
        assertThrows(EntityNotFoundException.class, ()-> ownerServiceSpy.getOwnersByPlotId(10));
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
        when(ownerRepositoryMock.findById(12)).thenReturn(Optional.of(ownerEntity));

        //Then
        ownerServiceSpy.deleteOwner(12, 1);

        verify(ownerRepositoryMock, times(1)).save(ownerEntity);
        verify(restUserMock, times(1)).deleteUser(12, 1);
    }

    @Test
    void deleteOwner_EntityNotFound() {
        when(ownerRepositoryMock.findById(12)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ownerServiceSpy.deleteOwner(12, 1));
        verify(ownerRepositoryMock, times(1)).findById(12);
        verify(ownerRepositoryMock, times(0)).save(Mockito.any(OwnerEntity.class));
        verify(restUserMock, times(0)).deleteUser(12, 1);
    }

    @Test
    void getallOwnersWithTheirPlots() {
        List<OwnerEntity> owners = Arrays.asList(OWNER_ENTITY_1, OWNER_ENTITY_2, OWNER_ENTITY_3);

        when(ownerRepositoryMock.findAllActives()).thenReturn(owners);
        when(plotOwnerRepositoryMock.findByOwnerId(OWNER_ENTITY_1.getId()))
                .thenReturn(Arrays.asList(PLOT_OWNER_ENTITY_1));
        when(plotOwnerRepositoryMock.findByOwnerId(OWNER_ENTITY_2.getId()))
                .thenReturn(Arrays.asList(PLOT_OWNER_ENTITY_2));
        when(plotOwnerRepositoryMock.findByOwnerId(OWNER_ENTITY_3.getId()))
                .thenReturn(Arrays.asList(PLOT_OWNER_ENTITY_3));


        List<GetOwnerWithHisPlots> result = ownerServiceSpy.getallOwnersWithTheirPlots();


        assertEquals(3, result.size());
        assertTrue(result.get(0).getPlot().contains(1));
        assertEquals(OWNER_DTO.getId(), result.get(1).getOwner().getId());
        assertTrue(result.get(1).getPlot().contains(2));
        assertTrue(result.get(2).getPlot().contains(3));
    }

    @Test
    void getDniTypes() {
        when(dniTypeRepository.findAll()).thenReturn(List.of(DNI_TYPE_ENTITY_DNI, DNI_TYPE_ENTITY_CUIT,
                DNI_TYPE_ENTITY_CUIL, DNI_TYPE_ENTITY_PASAPORTE));
        List<GetDniTypeDto> result = ownerServiceSpy.getDniTypes();
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("DNI", result.get(0).getDescription());
        assertEquals("CUIT", result.get(1).getDescription());
        assertEquals("CUIL", result.get(2).getDescription());
        assertEquals("Pasaporte", result.get(3).getDescription());

        verify(dniTypeRepository, times(1)).findAll();
        verify(modelMapper, times(4)).map(any(DniTypeEntity.class), eq(GetDniTypeDto.class));
    }

    @Test void createFileOwnerEntityTest()
    {
        FileEntity fileEntity = FileEntity.builder()
                .id(1)
                .fileUuid("123e4567-e89b-12d3-a456-426614174000")
                .name("documento_ejemplo.pdf")
                .createdUser(1001)
                .lastUpdatedUser(1002)
                .build();

        OwnerEntity ownerEntity = OWNER_ENTITY_1;
        FileOwnerEntity result = this.ownerServiceSpy.createFileOwnerEntity(fileEntity, ownerEntity, 1);
        assertNotNull(result);
    }

    @Test void createFileEntity() {
        Integer userId = 1;
        MultipartFile mockFile = new MockMultipartFile
                ("file", "documento.txt", "text/plain", "Contenido del archivo".getBytes());
        when(fileManagerClient.uploadFile(mockFile))
                .thenReturn(new FileClient(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")));
        FileEntity fileEntity = ownerServiceSpy.createFileEntity(mockFile, userId);
        assertNotNull(fileEntity);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", fileEntity.getFileUuid());
        assertEquals("documento.txt", fileEntity.getName());
        assertEquals(userId, fileEntity.getCreatedUser());
        verify(fileManagerClient, times(1)).uploadFile(mockFile);
    }
}



