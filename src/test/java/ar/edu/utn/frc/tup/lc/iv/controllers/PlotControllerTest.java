package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlotControllerTest {

    @MockBean
    private PlotService plotService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPlotStates() throws Exception {
        List<GetPlotStateDto> plotStates = Arrays.asList(
                new GetPlotStateDto(1, "State1"),
                new GetPlotStateDto(2, "State2")
        );

        Mockito.when(plotService.getPlotStates()).thenReturn(plotStates);

        mockMvc.perform(get("/plots/states"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("State1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("State2")));
    }

    @Test
    void getPlotTypes() throws Exception {
        List<GetPlotTypeDto> plotTypes = Arrays.asList(
                new GetPlotTypeDto(1, "Type1"),
                new GetPlotTypeDto(2, "Type2")
        );

        Mockito.when(plotService.getPlotTypes()).thenReturn(plotTypes);

        mockMvc.perform(get("/plots/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Type1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Type2")));
    }

    @Test
    void postPlot() throws Exception {
    }

    @Test
    void testPostPlot() throws Exception {
    }

    @Test
    void getPlots() throws Exception {

    }

    @Test
    void getAllPlotsAvailables() {
    }

    @Test
    void getPlotById() throws Exception {
    }
}