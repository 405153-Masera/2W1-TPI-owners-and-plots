package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlotOwnerServiceImplTest {

    @InjectMocks
    private PlotOwnerServiceImpl plotOwnerService;

    @Mock
    private PlotOwnerRepository plotOwnerRepository;

    @Mock
    private PlotRepository plotRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Test
    public void getAllPlotOwnerTest() {
        // Configuración de datos de prueba
        PlotEntity plotEntity1 = new PlotEntity();
        plotEntity1.setId(1);
        plotEntity1.setPlotNumber(123);
        plotEntity1.setBlockNumber(12);
        plotEntity1.setTotalAreaInM2(50D);
        plotEntity1.setBuiltAreaInM2(30D);

        OwnerTypeEntity ownerTypeEntity = new OwnerTypeEntity(1, "Propietario", LocalDateTime.now(), 1, LocalDateTime.now(), 2);

        TaxStatusEntity taxStatusEntity = new TaxStatusEntity(1, "Monotributista", LocalDateTime.now(), 1, LocalDateTime.now(), 2);

        OwnerEntity ownerEntity1 = new OwnerEntity();
        ownerEntity1.setId(1);
        ownerEntity1.setName("Bruno");
        ownerEntity1.setLastname("Diaz");
        ownerEntity1.setOwnerType(ownerTypeEntity);
        ownerEntity1.setTaxStatus(taxStatusEntity);

        PlotEntity plotEntity2 = new PlotEntity();
        plotEntity2.setId(2);
        plotEntity2.setPlotNumber(123);
        plotEntity2.setBlockNumber(12);
        plotEntity2.setTotalAreaInM2(50D);
        plotEntity2.setBuiltAreaInM2(30D);

        OwnerEntity ownerEntity2 = new OwnerEntity();
        ownerEntity2.setId(2);
        ownerEntity2.setName("Pilar");
        ownerEntity2.setLastname("Cifuentes");
        ownerEntity2.setOwnerType(ownerTypeEntity);
        ownerEntity2.setTaxStatus(taxStatusEntity);


        PlotOwnerEntity plotOwnerEntity1 = new PlotOwnerEntity();
        plotOwnerEntity1.setId(1);
        plotOwnerEntity1.setPlot(plotEntity1);
        plotOwnerEntity1.setOwner(ownerEntity1);

        PlotOwnerEntity plotOwnerEntity2 = new PlotOwnerEntity();
        plotOwnerEntity2.setId(2);
        plotOwnerEntity2.setPlot(plotEntity2);
        plotOwnerEntity2.setOwner(ownerEntity2);

        List<PlotOwnerEntity> plotOwnerEntities = new ArrayList<>();
        plotOwnerEntities.add(plotOwnerEntity1);
        plotOwnerEntities.add(plotOwnerEntity2);

        when(plotOwnerRepository.findAll()).thenReturn(plotOwnerEntities);


        List<GetPlotOwnerDto> result = plotOwnerService.getAllPlotOwner();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.get(0).getPlot_id());
        Assertions.assertEquals(1, result.get(0).getOwner_id());
        Assertions.assertEquals(2, result.get(1).getPlot_id());
        Assertions.assertEquals(2, result.get(1).getOwner_id());

    }

    @Test
    public void DeletePlotOwnerTest(){
        Integer ownerId = 1;
        Integer plotId = 1;

        plotOwnerService.deletePlotOwner(ownerId, plotId);

        verify(plotOwnerRepository, times(1)).deleteByOwnerIdAndPlotId(ownerId, plotId);
    }

    @Test
    public void createPlotOwnerTest(){
        Integer ownerId = 1;
        Integer plotId = 1;
        Integer userId = 1;

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(new OwnerEntity()));
        when(plotRepository.findById(plotId)).thenReturn(Optional.of(new PlotEntity()));

        plotOwnerService.createPlotOwner(ownerId, plotId, userId);

        verify(plotOwnerRepository, times(1)).save(any(PlotOwnerEntity.class));
    }
}