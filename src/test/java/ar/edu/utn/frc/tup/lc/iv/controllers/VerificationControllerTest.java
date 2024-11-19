package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.interfaces.VerificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerificationService verificationService;

    @Test
    void isPlotNumberUnique_ShouldReturnTrue() throws Exception {
        Mockito.when(verificationService.isPlotNumberUnique(123)).thenReturn(true);

        mockMvc.perform(get("/verification/plotnumber")
                        .param("plotNumber", "123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUnique").value(true));
    }

    @Test
    void isPlotNumberUnique_ShouldReturnFalse() throws Exception {
        Mockito.when(verificationService.isPlotNumberUnique(456)).thenReturn(false);

        mockMvc.perform(get("/verification/plotnumber")
                        .param("plotNumber", "456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUnique").value(false));
    }
}
