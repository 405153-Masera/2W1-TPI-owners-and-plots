package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlotOwnerController.class)
class PlotOwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlotOwnerService plotOwnerService;

    @MockBean
    private PlotService plotService;

    @Test
    void getUsers_ShouldReturnOkAndList() throws Exception {
        GetPlotOwnerDto mockDto = new GetPlotOwnerDto();
        mockDto.setPlot_id(1);
        mockDto.setOwner_id(1);
        List<GetPlotOwnerDto> mockResult = Collections.singletonList(mockDto);

        Mockito.when(plotOwnerService.getAllPlotOwner()).thenReturn(mockResult);

        mockMvc.perform(get("/plotOwners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUsers_ShouldReturnBadRequestWhenServiceReturnsNull() throws Exception {

        Mockito.when(plotOwnerService.getAllPlotOwner()).thenReturn(null);

        mockMvc.perform(get("/plotOwners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void transferPlotOwner_ShouldReturnOk() throws Exception {
        doNothing().when(plotService).transferPlot(1, 2, 3);

        mockMvc.perform(post("/plotOwners/transfer")
                        .param("plotId", "1")
                        .param("ownerId", "2")
                        .param("userId", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
