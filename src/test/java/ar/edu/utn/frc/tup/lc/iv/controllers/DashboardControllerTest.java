package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.dashboard.*;
import ar.edu.utn.frc.tup.lc.iv.services.dashboard.OwnerStatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerStatsService ownerStatsService;

    @Test
    void getBlocksData() throws Exception {
        BlockData blockData1 = new BlockData(100, 1000, 600);
        BlockData blockData2 = new BlockData(101, 2000, 1000);
        List<BlockData> blockDataList = Arrays.asList(blockData1, blockData2);

        when(ownerStatsService.getBlocksData(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(blockDataList);

        mockMvc.perform(get("/dashboard/blockStats")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].blockNumber").value(100))
                .andExpect(jsonPath("$[0].totalArea").value(1000))
                .andExpect(jsonPath("$[0].builtArea").value(600));
    }

    @Test
    void getPlotByStateCount() throws Exception {
        PlotByPlotStateCountDTO data1 = new PlotByPlotStateCountDTO("En Construcción", 10);
        PlotByPlotStateCountDTO data2 = new PlotByPlotStateCountDTO("Disponible", 20);
        List<PlotByPlotStateCountDTO> plotStateList = Arrays.asList(data1, data2);

        when(ownerStatsService.countPlotsByState(any(LocalDate.class), any(LocalDate.class), isNull()))
                .thenReturn(plotStateList);

        mockMvc.perform(get("/dashboard/Plot-By-State-Count")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .param("plotType", (String) null)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].state").value("En Construcción"))
                .andExpect(jsonPath("$[0].count").value(10))
                .andExpect(jsonPath("$[1].state").value("Disponible"))
                .andExpect(jsonPath("$[1].count").value(20));
    }

    @Test
    void getPlotByTypeCount() throws Exception {
        PlotByPlotTypeCountDTO data1 = new PlotByPlotTypeCountDTO("Residencial", 10);
        PlotByPlotTypeCountDTO data2 = new PlotByPlotTypeCountDTO("Comercial", 20);
        List<PlotByPlotTypeCountDTO> plotTypeList = Arrays.asList(data1, data2);

        when(ownerStatsService.countPlotsByType(null, null))
                .thenReturn(plotTypeList);

        mockMvc.perform(get("/dashboard/Plot-By-Type-Count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].type").value("Residencial"))
                .andExpect(jsonPath("$[0].count").value(10));
    }

    @Test
    void getGeneralStats() throws Exception {
        PlotsStats plotsStats = new PlotsStats(20, 10, 5, 5, 5000, 2500);

        when(ownerStatsService.getStatsOfPlots(
                any(LocalDate.class),
                any(LocalDate.class),
                any(String.class),
                any(String.class)))
                .thenReturn(plotsStats);

        mockMvc.perform(get("/dashboard/plots-stats")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .param("plotType", "Residencial")
                        .param("plotStatus", "Disponible")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPlots").value(20))
                .andExpect(jsonPath("$.availablePlots").value(10))
                .andExpect(jsonPath("$.constructionPlots").value(5))
                .andExpect(jsonPath("$.occupiedPlots").value(5));
    }

    @Test
    void getPlotsByBlock() throws Exception {
        PlotsByBlock data1 = new PlotsByBlock(11, 5, 3, 2, 10);
        PlotsByBlock data2 = new PlotsByBlock(/* add necessary parameters */);
        List<PlotsByBlock> plotsByBlockList = Arrays.asList(data1, data2);

        when(ownerStatsService.getPlotsByBlock(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(plotsByBlockList);

        mockMvc.perform(get("/dashboard/plots-by-block")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].blockNumber").value(11))
                .andExpect(jsonPath("$[0].occupied").value(5))
                .andExpect(jsonPath("$[0].available").value(3));
    }

    @Test
    void getOwnershipDistribution() throws Exception {
        OwnersPlotsDistribution data1 = new OwnersPlotsDistribution(/* add necessary parameters */);
        OwnersPlotsDistribution data2 = new OwnersPlotsDistribution(/* add necessary parameters */);
        List<OwnersPlotsDistribution> distributionList = Arrays.asList(data1, data2);

        when(ownerStatsService.getOwnersPlotsDistribution(
                null,
                null,
                null,
                null))
                .thenReturn(distributionList);

        mockMvc.perform(get("/dashboard/owners-distribution")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
