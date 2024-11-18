package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OwnerStatsServiceTest {

    @MockBean
    private PlotRepository plotRepository;

    @MockBean
    private PlotOwnerRepository plotOwnerRepository;

    @Autowired
    private OwnerStatsService ownerStatsService;

    private PlotEntity plot1, plot2;
    private PlotOwnerEntity plotOwner1, plotOwner2;
    private LocalDate startDate, endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);

        PlotStateEntity availableState = new PlotStateEntity();
        availableState.setId(1);
        availableState.setName("Disponible");

        PlotStateEntity occupiedState = new PlotStateEntity();
        occupiedState.setId(2);
        occupiedState.setName("Habitado");

        PlotTypeEntity comercialType = new PlotTypeEntity();
        comercialType.setId(1);
        comercialType.setName("Comercial");

        plot1 = new PlotEntity();
        plot1.setId(1);
        plot1.setBlockNumber(1);
        plot1.setTotalAreaInM2(2000.0);
        plot1.setBuiltAreaInM2(1500.0);
        plot1.setPlotState(availableState);
        plot1.setPlotType(comercialType);
        plot1.setCreatedDatetime(LocalDateTime.of(2024, 2, 1, 0, 0));

        plot2 = new PlotEntity();
        plot2.setId(2);
        plot2.setBlockNumber(1);
        plot2.setTotalAreaInM2(3000.0);
        plot2.setBuiltAreaInM2(2000.0);
        plot2.setPlotState(occupiedState);
        plot2.setPlotType(comercialType);
        plot2.setCreatedDatetime(LocalDateTime.of(2024, 3, 1, 0, 0));

        OwnerTypeEntity ownerType = new OwnerTypeEntity();
        ownerType.setId(1);
        ownerType.setDescription("Persona Física");

        OwnerEntity owner1 = new OwnerEntity();
        owner1.setId(1);
        owner1.setName("John");
        owner1.setLastname("Mas");
        owner1.setOwnerType(ownerType);

        plotOwner1 = new PlotOwnerEntity();
        plotOwner1.setOwner(owner1);
        plotOwner1.setPlot(plot1);

        plotOwner2 = new PlotOwnerEntity();
        plotOwner2.setOwner(owner1);
        plotOwner2.setPlot(plot2);
    }

    @Test
    void getBlocksData() {
        when(plotRepository.findAll()).thenReturn(Arrays.asList(plot1, plot2));

        List<BlockData> result = ownerStatsService.getBlocksData(startDate, endDate);

        assertEquals(1, result.size());
        BlockData blockData = result.get(0);
        assertEquals(1, blockData.getBlockNumber());
        assertEquals(5000, blockData.getTotalArea());
        assertEquals(3500, blockData.getBuiltArea());
    }

    @Test
    void getStatsOfPlots() {
        when(plotRepository.findAll()).thenReturn(Arrays.asList(plot1, plot2));

        PlotsStats result = ownerStatsService.getStatsOfPlots(startDate, endDate, null, null);

        assertEquals(2, result.getTotalPlots());
        assertEquals(1, result.getAvailablePlots());
        assertEquals(0, result.getConstructionPlots());
        assertEquals(1, result.getOccupiedPlots());
        assertEquals(5000, result.getTotalArea());
        assertEquals(3500, result.getConstructedArea());
    }

    @Test
    void getOwnersPlotsDistribution() {
        when(plotRepository.findAll()).thenReturn(Arrays.asList(plot1, plot2));
        when(plotOwnerRepository.findAll()).thenReturn(Arrays.asList(plotOwner1, plotOwner2));

        List<OwnersPlotsDistribution> result = ownerStatsService.getOwnersPlotsDistribution(
                startDate, endDate, null, null);

        assertEquals(1, result.size());
        OwnersPlotsDistribution distribution = result.get(0);
        assertEquals("John Mas", distribution.getOwnerName());
        assertEquals(2, distribution.getPlotCount());
        assertEquals(5000, distribution.getTotalArea());
    }

    @Test
    void getPlotsByBlock() {
        when(plotRepository.findAll()).thenReturn(Arrays.asList(plot1, plot2));

        List<PlotsByBlock> result = ownerStatsService.getPlotsByBlock(startDate, endDate);

        assertEquals(1, result.size());
        PlotsByBlock blockStats = result.get(0);
        assertEquals(1, blockStats.getBlockNumber());
        assertEquals(1, blockStats.getAvailable());
        assertEquals(1, blockStats.getOccupied());
        assertEquals(0, blockStats.getInConstruction());
        assertEquals(2, blockStats.getTotal());
    }

    @Test
    void countPlotsByState() {
        List<Object[]> mockResult = Arrays.asList(
                new Object[]{"Disponible", 10L},
                new Object[]{"Habitado", 20L}
        );
        when(plotRepository.countPlotsByState(startDate, endDate, null))
                .thenReturn(mockResult);

        List<PlotByPlotStateCountDTO> result = ownerStatsService.countPlotsByState(
                startDate, endDate, null);

        assertEquals(2, result.size());
        assertEquals("Disponible", result.get(0).getState());
        assertEquals(10, result.get(0).getCount());
        assertEquals("Habitado", result.get(1).getState());
        assertEquals(20, result.get(1).getCount());
    }

    @Test
    void countPlotsByType() {
        List<Object[]> mockResult = Arrays.asList(
                new Object[]{"Residencial", 20},
                new Object[]{"Habitado", 15}
        );
        when(plotRepository.countPlotsByType(startDate, endDate))
                .thenReturn(mockResult);

        List<PlotByPlotTypeCountDTO> result = ownerStatsService.countPlotsByType(
                startDate, endDate);

        assertEquals(2, result.size());
        assertEquals("Residencial", result.get(0).getType());
        assertEquals(20, result.get(0).getCount());
    }

    @Test
    void getFiltersPlots() {
        when(plotRepository.findAll()).thenReturn(Arrays.asList(plot1, plot2));

        List<PlotEntity> result = ownerStatsService.getFiltersPlots(
                startDate, endDate, "Comercial", "Disponible");

        assertEquals(1, result.size());
        assertEquals(plot1.getId(), result.get(0).getId());
    }
}
