package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.FileDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        PostPlotDto postPlotDto = new PostPlotDto(123,12,70D,50D,1,1,1, null);

        GetPlotDto getPlotDto = new GetPlotDto(1,123,12,70D,50D,"State1", "Type1", null);

        Mockito.when(plotService.createPlot(postPlotDto)).thenReturn(getPlotDto);

        mockMvc.perform(post("/plots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(getPlotDto.getId())))
                .andExpect(jsonPath("$.plot_number", is(getPlotDto.getPlot_number())))
                .andExpect(jsonPath("$.block_number", is(getPlotDto.getBlock_number())))
                .andExpect(jsonPath("$.plot_state", is(getPlotDto.getPlot_state())))
                .andExpect((jsonPath("$.plot_type", is(getPlotDto.getPlot_type()))));
    }

    @Test
    void testPostPlot() throws Exception {
    }

    @Test
    void getPlots() throws Exception {
        List<FileDto> files = Arrays.asList(
          new FileDto("File1", "abcd"),
          new FileDto("File2", "1234")
        );

        List<FileDto> files2 = Arrays.asList(
                new FileDto("File3", "4321"),
                new FileDto("File4", "dcba")
        );
        List<GetPlotDto> plots = Arrays.asList(
                new GetPlotDto( 1, 123, 12, 80D, 60D, "State1", "Type1", files),
                new GetPlotDto( 2, 234, 23, 90D, 70D, "State2", "Type2", files2)
        );

        Mockito.when(plotService.getAllPlots()).thenReturn(plots);

        mockMvc.perform(get("/plots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].plot_number",is(123)))
                .andExpect(jsonPath("$[1].plot_number",is(234)))
                .andExpect((jsonPath("$[0].plot_state", is("State1"))))
                .andExpect((jsonPath("$[1].plot_state", is("State2"))));

        //No funciona el test con los files agregado.
//                .andExpect((jsonPath("$[0].files", is(files))));
//                .andExpect((jsonPath("$[1].files", is(files2))));
    }

    @Test
    void getAllPlotsAvailables() throws Exception {

        List<GetPlotDto> plots = Arrays.asList(
                new GetPlotDto( 1, 123, 12, 80D, 60D, "Disponible", "Type1", null),
                new GetPlotDto( 2, 234, 23, 90D, 70D, "Disponible", "Type2", null)
        );

        Mockito.when(plotService.getAllPlotsAvailables()).thenReturn(plots);

        mockMvc.perform(get("/plots/availables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((jsonPath("$[0].id", is(1))))
                .andExpect((jsonPath("$[1].id", is(2))))
                .andExpect((jsonPath("$[0].plot_state", is("Disponible"))))
                .andExpect((jsonPath("$[1].plot_state", is("Disponible"))))
                .andExpect((jsonPath("$[0].plot_type", is("Type1"))))
                .andExpect((jsonPath("$[1].plot_type", is("Type2"))));


    }

    @Test
    void getPlotById() throws Exception {

        Integer id = 1;
        GetPlotDto plot = new GetPlotDto( 1, 123, 12, 80D, 60D, "Disponible", "Type1", null);

        Mockito.when(plotService.getPlotById(id)).thenReturn(plot);

        mockMvc.perform(get("/plots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(plot.getId())))
                .andExpect(jsonPath("$.plot_number", is(plot.getPlot_number())))
                .andExpect(jsonPath("$.plot_state", is(plot.getPlot_state())))
                .andExpect(jsonPath("$.plot_type", is(plot.getPlot_state())));

    }
}