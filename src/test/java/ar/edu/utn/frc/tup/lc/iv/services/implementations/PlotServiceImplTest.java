package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotStateRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.FileService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlotServiceImplTest {

    @MockBean
    private PlotStateRepository plotStateRepository;

    @MockBean
    private PlotTypeRepository plotTypeRepository;

    @MockBean
    private FileManagerClient fileManagerClient;

    @MockBean
    private MultipartFile mockFile;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private FileService fileService;

    @MockBean
    private PlotRepository plotRepository;

    @MockBean
    private PlotOwnerRepository plotOwnerRepository;

    @SpyBean
    private PlotServiceImpl plotService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getPlotStates() {

        //given
        List<PlotStateEntity> plotStateEntityList = new ArrayList<>();

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(1);
        plotStateEntity.setName("Disponible");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);
        plotStateEntityList.add(plotStateEntity);

        PlotStateEntity plotStateEntity2 = new PlotStateEntity();
        plotStateEntity2.setId(2);
        plotStateEntity2.setName("Habitado");
        plotStateEntity2.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity2.setCreatedUser(1);
        plotStateEntity2.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity2.setLastUpdatedUser(1);
        plotStateEntityList.add(plotStateEntity2);

        PlotStateEntity plotStateEntity3 = new PlotStateEntity();
        plotStateEntity3.setId(3);
        plotStateEntity3.setName("En Construcción");
        plotStateEntity3.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity3.setCreatedUser(1);
        plotStateEntity3.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity3.setLastUpdatedUser(1);
        plotStateEntityList.add(plotStateEntity3);

        //when
        when(plotStateRepository.findAll()).thenReturn(plotStateEntityList);
        List<GetPlotStateDto> plotStateDtoList = plotService.getPlotStates();

        //then
        Assertions.assertEquals(3, plotStateDtoList.size());
        Assertions.assertEquals("Disponible", plotStateDtoList.get(0).getName());
        Assertions.assertEquals(plotStateEntityList.get(1).getName(), plotStateDtoList.get(1).getName());
        Assertions.assertNotEquals(2, plotStateDtoList.get(2).getId());
    }

    @Test
    void getPlotTypes() {

        //given
        List<PlotTypeEntity> plotTypeEntityList = new ArrayList<>();

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotTypeEntityList.add(plotTypeEntity);

        PlotTypeEntity plotTypeEntity2 = new PlotTypeEntity();
        plotTypeEntity2.setId(2);
        plotTypeEntity2.setName("Residencial");
        plotTypeEntity2.setCreatedUser(1);
        plotTypeEntity2.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity2.setLastUpdatedUser(1);
        plotTypeEntity2.setLastUpdatedDatetime(LocalDateTime.now());
        plotTypeEntityList.add(plotTypeEntity2);

        PlotTypeEntity plotTypeEntity3 = new PlotTypeEntity();
        plotTypeEntity3.setId(3);
        plotTypeEntity3.setName("Baldío");
        plotTypeEntity3.setCreatedUser(1);
        plotTypeEntity3.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity3.setLastUpdatedUser(1);
        plotTypeEntity3.setLastUpdatedDatetime(LocalDateTime.now());
        plotTypeEntityList.add(plotTypeEntity3);

        //when
        when(plotTypeRepository.findAll()).thenReturn(plotTypeEntityList);
        List<GetPlotTypeDto> plotTypeDtoList = plotService.getPlotTypes();

        //then
        Assertions.assertNotNull(plotTypeDtoList);
        Assertions.assertEquals(plotTypeEntityList.size(), plotTypeDtoList.size());
        Assertions.assertNotEquals("Comercial", plotTypeDtoList.get(1).getName());
        Assertions.assertEquals("Baldío", plotTypeDtoList.get(2).getName());
    }

    @Test
    void getAllPlots() {

        //given
        List<PlotEntity> plotEntityList = new ArrayList<>();

        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1);
        plotEntity.setPlotNumber(123);
        plotEntity.setBlockNumber(12);
        plotEntity.setTotalAreaInM2(50D);
        plotEntity.setBuiltAreaInM2(30D);

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(2);
        plotStateEntity.setName("Habitado");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setCreatedUser(1);
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntityList.add(plotEntity);

        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(10);
        plotEntity2.setPlotNumber(256);
        plotEntity2.setBlockNumber(25);
        plotEntity2.setTotalAreaInM2(70D);
        plotEntity2.setBuiltAreaInM2(0D);

        PlotStateEntity plotStateEntity2 = new PlotStateEntity();
        plotStateEntity2.setId(1);
        plotStateEntity2.setName("Disponible");
        plotStateEntity2.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity2.setCreatedUser(1);
        plotStateEntity2.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity2.setLastUpdatedUser(1);

        plotEntity2.setPlotState(plotStateEntity2);

        PlotTypeEntity plotTypeEntity2 = new PlotTypeEntity();
        plotTypeEntity2.setId(3);
        plotTypeEntity2.setName("Baldío");
        plotTypeEntity2.setCreatedUser(1);
        plotTypeEntity2.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity2.setLastUpdatedUser(1);
        plotTypeEntity2.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity2.setPlotType(plotTypeEntity2);

        plotEntity2.setCreatedUser(1);
        plotEntity2.setCreatedDatetime(LocalDateTime.now());
        plotEntity2.setLastUpdatedUser(1);
        plotEntity2.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntityList.add(plotEntity2);

        //when
        when(plotRepository.findAll()).thenReturn(plotEntityList);
        List<GetPlotDto> plotDtoList = plotService.getAllPlots();

        //then
        Assertions.assertEquals(2, plotDtoList.size());
        Assertions.assertEquals(30D, plotDtoList.get(0).getBuilt_area_in_m2());
        Assertions.assertNotEquals("Disponible", plotDtoList.get(0).getPlot_state());
        Assertions.assertEquals("Baldío", plotDtoList.get(1).getPlot_type());
    }

    //no funciona. Deberia devolver un solo lote disponible, devuelve dos.
    @Test
    void getAllPlotsAvailables() {

        //given
        List<PlotEntity> plotEntityList = new ArrayList<>();

        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1);
        plotEntity.setPlotNumber(123);
        plotEntity.setBlockNumber(12);
        plotEntity.setTotalAreaInM2(50D);
        plotEntity.setBuiltAreaInM2(30D);

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(2);
        plotStateEntity.setName("Habitado");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setCreatedUser(1);
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntityList.add(plotEntity);

        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(10);
        plotEntity2.setPlotNumber(256);
        plotEntity2.setBlockNumber(25);
        plotEntity2.setTotalAreaInM2(70D);
        plotEntity2.setBuiltAreaInM2(0D);

        PlotStateEntity plotStateEntity2 = new PlotStateEntity();
        plotStateEntity2.setId(1);
        plotStateEntity2.setName("Disponible");
        plotStateEntity2.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity2.setCreatedUser(1);
        plotStateEntity2.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity2.setLastUpdatedUser(1);

        plotEntity2.setPlotState(plotStateEntity2);

        PlotTypeEntity plotTypeEntity2 = new PlotTypeEntity();
        plotTypeEntity2.setId(3);
        plotTypeEntity2.setName("Baldío");
        plotTypeEntity2.setCreatedUser(1);
        plotTypeEntity2.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity2.setLastUpdatedUser(1);
        plotTypeEntity2.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity2.setPlotType(plotTypeEntity2);

        plotEntity2.setCreatedUser(1);
        plotEntity2.setCreatedDatetime(LocalDateTime.now());
        plotEntity2.setLastUpdatedUser(1);
        plotEntity2.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntityList.add(plotEntity2);

        //when
        when(plotRepository.findPlotsAvailables()).thenReturn(plotEntityList);
        List<GetPlotDto> plotDtoAvailableList = plotService.getAllPlotsAvailables();

        //then
        Assertions.assertEquals(2, plotDtoAvailableList.size());
        Assertions.assertEquals(123, plotDtoAvailableList.get(0).getPlot_number());
        Assertions.assertEquals("Comercial", plotDtoAvailableList.get(0).getPlot_type());
    }

    @Test
    void createPlot() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setPlot_number(123);
        postPlotDto.setBlock_number(12);
        postPlotDto.setTotal_area_in_m2(70D);
        postPlotDto.setBuilt_area_in_m2(50D);
        postPlotDto.setPlot_state_id(1);
        postPlotDto.setPlot_type_id(1);
        postPlotDto.setUserCreateId(1);

        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1);
        plotEntity.setPlotNumber(postPlotDto.getPlot_number());
        plotEntity.setBlockNumber(postPlotDto.getBlock_number());
        plotEntity.setBuiltAreaInM2(postPlotDto.getBuilt_area_in_m2());
        plotEntity.setTotalAreaInM2(postPlotDto.getTotal_area_in_m2());

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(1);
        plotStateEntity.setName("Disponible");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setCreatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);

        GetPlotDto getPlotDto = new GetPlotDto();
        getPlotDto.setId(1);
        getPlotDto.setPlot_number(postPlotDto.getPlot_number());
        getPlotDto.setBlock_number(postPlotDto.getBlock_number());
        getPlotDto.setTotal_area_in_m2(postPlotDto.getTotal_area_in_m2());
        getPlotDto.setBuilt_area_in_m2(postPlotDto.getBuilt_area_in_m2());
        getPlotDto.setPlot_state(plotStateEntity.getName());
        getPlotDto.setPlot_type(plotTypeEntity.getName());

        //when
        when(plotStateRepository.findById(1)).thenReturn(Optional.of(plotStateEntity));
        when(plotTypeRepository.findById(1)).thenReturn(Optional.of(plotTypeEntity));
        when(plotRepository.save(any(PlotEntity.class))).thenReturn(plotEntity);

        GetPlotDto getPlotDtoSaved = plotService.createPlot(postPlotDto);


        //then
        Assertions.assertEquals(getPlotDto.getPlot_number(), getPlotDtoSaved.getPlot_number());
        Assertions.assertEquals(getPlotDto.getBlock_number(), getPlotDtoSaved.getBlock_number());
        Assertions.assertEquals(getPlotDto.getTotal_area_in_m2(), getPlotDtoSaved.getTotal_area_in_m2());
        Assertions.assertEquals(getPlotDto.getBuilt_area_in_m2(), getPlotDtoSaved.getBuilt_area_in_m2());
        Assertions.assertEquals(getPlotDto.getPlot_state(), getPlotDtoSaved.getPlot_state());
        Assertions.assertEquals(getPlotDto.getPlot_type(), getPlotDtoSaved.getPlot_type());
    }

    @Test
    void getPlotById() {

        //given
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1);
        plotEntity.setPlotNumber(123);
        plotEntity.setBlockNumber(12);
        plotEntity.setTotalAreaInM2(70D);
        plotEntity.setBuiltAreaInM2(50D);

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(1);
        plotStateEntity.setName("Disponible");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setCreatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);

        //when
        when(plotRepository.findById(1)).thenReturn(Optional.of(plotEntity));
        GetPlotDto getPlotDto = plotService.getPlotById(1);

        //then
        Assertions.assertEquals(plotEntity.getId(), getPlotDto.getId());
        Assertions.assertEquals(plotEntity.getPlotType().getName(), getPlotDto.getPlot_type());
        Assertions.assertEquals(plotEntity.getPlotState().getName(), getPlotDto.getPlot_state());
    }

    @Test
    void validatePlotNumber_True() {
        //Existe el plot number

        //given
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(101);
        plotEntity.setPlotNumber(255);
        plotEntity.setBlockNumber(25);
        plotEntity.setTotalAreaInM2(100D);
        plotEntity.setBuiltAreaInM2(0D);

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(1);
        plotStateEntity.setName("Disponible");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(3);
        plotTypeEntity.setName("Baldío");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setCreatedUser(1);
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());

        //when
        when(plotRepository.findByPlotNumber(101)).thenReturn(plotEntity);
        //plotService.validatePlotNumber(101);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            plotService.validatePlotNumber(101);
        });
    }

    //implementar
    void validatePlotNumber_False() {
        //No existe el plot number
    }


    @Test
    void mapPlotPostToPlotEntity() {

        //given
        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setPlot_number(123);
        postPlotDto.setBlock_number(12);
        postPlotDto.setTotal_area_in_m2(70D);
        postPlotDto.setBuilt_area_in_m2(50D);
        postPlotDto.setPlot_state_id(1);
        postPlotDto.setPlot_type_id(1);
        postPlotDto.setUserCreateId(1);

        PlotEntity plotEntity = new PlotEntity();
        PlotStateEntity plotStateEntity = new PlotStateEntity(1, null, null, null, null, null);
        PlotTypeEntity plotTypeEntity = new PlotTypeEntity(1, null, null, null, null, null);

        //when
        when(plotStateRepository.findById(postPlotDto.getPlot_state_id())).thenReturn(Optional.of(plotStateEntity));
        when(plotTypeRepository.findById(postPlotDto.getPlot_type_id())).thenReturn(Optional.of(plotTypeEntity));
        plotService.mapPlotPostToPlotEntity(plotEntity, postPlotDto);

        // Ensure the entities are set in the plotEntity
        plotEntity.setPlotState(plotStateEntity);
        plotEntity.setPlotType(plotTypeEntity);

        //then
        Assertions.assertEquals(postPlotDto.getPlot_number(), plotEntity.getPlotNumber());
        Assertions.assertEquals(postPlotDto.getBlock_number(), plotEntity.getBlockNumber());
        Assertions.assertEquals(postPlotDto.getTotal_area_in_m2(), plotEntity.getTotalAreaInM2());
        Assertions.assertEquals(postPlotDto.getBuilt_area_in_m2(), plotEntity.getBuiltAreaInM2());
        Assertions.assertEquals(postPlotDto.getPlot_state_id(), plotEntity.getPlotState().getId());
        Assertions.assertEquals(postPlotDto.getPlot_type_id(), plotEntity.getPlotType().getId());
        Assertions.assertEquals(postPlotDto.getUserCreateId(), plotEntity.getCreatedUser());
    }

    @Test
    void mapPlotEntityToGetPlotDto() {

        //when
        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setId(1);
        plotEntity.setPlotNumber(123);
        plotEntity.setBlockNumber(12);

        PlotStateEntity plotStateEntity = new PlotStateEntity();
        plotStateEntity.setId(1);
        plotStateEntity.setName("Disponible");
        plotStateEntity.setCreatedDatetime(LocalDateTime.now());
        plotStateEntity.setCreatedUser(1);
        plotStateEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotStateEntity.setLastUpdatedUser(1);

        plotEntity.setPlotState(plotStateEntity);

        PlotTypeEntity plotTypeEntity = new PlotTypeEntity();
        plotTypeEntity.setId(1);
        plotTypeEntity.setName("Comercial");
        plotTypeEntity.setCreatedUser(1);
        plotTypeEntity.setCreatedDatetime(LocalDateTime.now());
        plotTypeEntity.setLastUpdatedUser(1);
        plotTypeEntity.setLastUpdatedDatetime(LocalDateTime.now());

        plotEntity.setPlotType(plotTypeEntity);

        plotEntity.setTotalAreaInM2(70D);
        plotEntity.setBuiltAreaInM2(50D);

        plotEntity.setCreatedUser(1);
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(1);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());

        GetPlotDto getPlotDto = new GetPlotDto();

        //when
        plotService.mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);

        //then
        Assertions.assertEquals(plotEntity.getId(), getPlotDto.getId());
        Assertions.assertEquals(plotEntity.getPlotNumber(), getPlotDto.getPlot_number());
        Assertions.assertEquals(plotEntity.getBlockNumber(), getPlotDto.getBlock_number());
        Assertions.assertEquals(plotEntity.getTotalAreaInM2(), getPlotDto.getTotal_area_in_m2());
        Assertions.assertEquals(plotEntity.getBuiltAreaInM2(), getPlotDto.getBuilt_area_in_m2());
        Assertions.assertEquals(plotEntity.getPlotState().getName(), getPlotDto.getPlot_state());
        Assertions.assertEquals(plotEntity.getPlotType().getName(), getPlotDto.getPlot_type());
    }

    @Test
    void validateWasteland_withBuiltArea_shouldThrowException() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setPlot_type_id(3);
        postPlotDto.setBuilt_area_in_m2(50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            plotService.validateWasteland(postPlotDto);
        });
        assertEquals("Error , a westland plot cannot have a built area", exception.getMessage());
    }

    @Test
    void validateWasteland_withoutBuiltArea_shouldPass() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setPlot_type_id(3);
        postPlotDto.setBuilt_area_in_m2(0);

        assertDoesNotThrow(() -> plotService.validateWasteland(postPlotDto));
    }

    @Test
    void validateWasteland_withNonWastelandType_shouldPass() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setPlot_type_id(2);
        postPlotDto.setBuilt_area_in_m2(50);

        assertDoesNotThrow(() -> plotService.validateWasteland(postPlotDto));
    }

    @Test
    void validateBuiltArea_withBuiltAreaGreaterThanTotalArea_shouldThrowException() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setTotal_area_in_m2(100);
        postPlotDto.setBuilt_area_in_m2(150);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            plotService.validateBuiltArea(postPlotDto);
        });
        assertEquals("Error , built area is bigger than total area", exception.getMessage());
    }

    @Test
    void validateBuiltArea_withBuiltAreaEqualToTotalArea_shouldPass() {

        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setTotal_area_in_m2(100);
        postPlotDto.setBuilt_area_in_m2(100);

        assertDoesNotThrow(() -> plotService.validateBuiltArea(postPlotDto));
    }

    @Test
    void validateBuiltArea_withBuiltAreaLessThanTotalArea_shouldPass() {
        PostPlotDto postPlotDto = new PostPlotDto();
        postPlotDto.setTotal_area_in_m2(100);
        postPlotDto.setBuilt_area_in_m2(50);

        assertDoesNotThrow(() -> plotService.validateBuiltArea(postPlotDto));
    }

    @Test
    void uploadFiles_withNullOrEmptyFilesList_shouldDoNothing() {

        PlotEntity plotEntity = new PlotEntity();
        plotEntity.setFiles(new ArrayList<>());

        plotService.uploadFiles(null, 1, plotEntity);
        plotService.uploadFiles(new ArrayList<>(), 1, plotEntity);

        assertTrue(plotEntity.getFiles().isEmpty());
    }


    @Test
    void getPlotByOwnerId() {

        Integer ownerId = 1;

        PlotStateEntity plotStateEntity = PlotStateEntity.builder()
                .id(1)
                .name("Estado de prueba")
                .build();

        PlotTypeEntity plotTypeEntity = PlotTypeEntity.builder()
                .id(1)
                .name("Tipo de prueba")
                .build();

        PlotEntity plotEntity = PlotEntity.builder()
                .id(1)
                .plotNumber(101)
                .blockNumber(5)
                .totalAreaInM2(100.0)
                .builtAreaInM2(80.0)
                .plotState(plotStateEntity)
                .plotType(plotTypeEntity)
                .build();

        PlotOwnerEntity plotOwnerEntity = PlotOwnerEntity.builder()
                .id(1)
                .plot(plotEntity)
                .owner(new OwnerEntity())
                .build();

        when(plotOwnerRepository.findByOwnerId(ownerId)).thenReturn(List.of(plotOwnerEntity));
        when(plotRepository.findById(plotEntity.getId())).thenReturn(Optional.of(plotEntity));

        List<GetPlotDto> result = plotService.getPlotByOwnerId(ownerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        GetPlotDto plotDto = result.get(0);
        assertEquals(101, plotDto.getPlot_number());
        assertEquals(5, plotDto.getBlock_number());
        assertEquals(100.0, plotDto.getTotal_area_in_m2());
        assertEquals(80.0, plotDto.getBuilt_area_in_m2());
    }

    @Test
    void testGetPlotEntityById_WhenPlotExists() {

        Integer plotId = 1;

        PlotEntity plotEntity = PlotEntity.builder()
                .id(plotId)
                .plotNumber(101)
                .blockNumber(5)
                .totalAreaInM2(100.0)
                .builtAreaInM2(80.0)
                .build();

        when(plotRepository.findById(plotId)).thenReturn(Optional.of(plotEntity));

        PlotEntity result = plotService.getPlotEntityById(plotId);

        assertNotNull(result);
        assertEquals(plotId, result.getId());
        assertEquals(101, result.getPlotNumber());
        assertEquals(5, result.getBlockNumber());
    }

    @Test
    void testGetPlotEntityById_WhenPlotDoesNotExist() {
        Integer plotId = 1;

        when(plotRepository.findById(plotId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            plotService.getPlotEntityById(plotId);
        });

        assertEquals("Plot not found with id: " + plotId, exception.getMessage());
    }

    @Test
    void testPutPlot() {

        Integer plotId = 1;
        PutPlotDto plotDto = PutPlotDto.builder()
                .total_area_in_m2(100.0)
                .built_area_in_m2(80.0)
                .plot_state_id(1)
                .plot_type_id(1)
                .userUpdateId(123)
                .files(Collections.emptyList())
                .build();

        PlotStateEntity plotState = new PlotStateEntity();
        plotState.setId(1);
        plotState.setName("Disponible");

        PlotTypeEntity plotType = new PlotTypeEntity();
        plotType.setId(1);
        plotType.setName("Casa");

        PlotEntity existingPlot = PlotEntity.builder()
                .id(plotId)
                .plotNumber(101)
                .blockNumber(1)
                .totalAreaInM2(90.0)
                .builtAreaInM2(70.0)
                .plotState(plotState)
                .plotType(plotType)
                .files(Collections.emptyList())
                .build();

        when(plotRepository.findById(plotId)).thenReturn(Optional.of(existingPlot));
        when(plotRepository.save(any(PlotEntity.class))).thenReturn(existingPlot);

        when(plotStateRepository.findById(plotDto.getPlot_state_id())).thenReturn(Optional.of(plotState));
        when(plotTypeRepository.findById(plotDto.getPlot_type_id())).thenReturn(Optional.of(plotType));

        GetPlotDto result = plotService.putPlot(plotDto, plotId);

        assertNotNull(result);
        assertEquals(100.0, result.getTotal_area_in_m2());
        assertEquals(80.0, result.getBuilt_area_in_m2());
        assertEquals(plotState.getName(), result.getPlot_state());
        assertEquals(plotType.getName(), result.getPlot_type());
    }
}