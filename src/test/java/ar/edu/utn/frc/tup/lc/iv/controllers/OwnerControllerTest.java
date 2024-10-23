package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerAndPlot;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    void postOwner() throws Exception {

        PostOwnerDto postOwnerDto = new PostOwnerDto();
        postOwnerDto.setName("John Doe");

        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(1);
        getOwnerDto.setName("John Doe");

        Mockito.when(ownerService.createOwner(Mockito.any(PostOwnerDto.class))).thenReturn(getOwnerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("postOwnerDto", postOwnerDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void putOwner() throws Exception {
        Integer ownerId = 1;
        PutOwnerDto putOwnerDto = new PutOwnerDto();
        putOwnerDto.setName("John Doe Updated");

        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(ownerId);
        getOwnerDto.setName("John Doe Updated");

        Mockito.when(ownerService.updateOwner(Mockito.eq(ownerId), Mockito.any(PutOwnerDto.class))).thenReturn(getOwnerDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/owners/{ownerId}", ownerId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("putOwnerDto", putOwnerDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ownerId))
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    void getOwnerById() throws Exception {
        GetOwnerDto mockOwner = new GetOwnerDto();
        mockOwner.setId(1);
        mockOwner.setName("John Doe");

        Mockito.when(ownerService.getById(anyInt())).thenReturn(mockOwner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));

    }

    @Test
    void getTaxStatus() throws Exception {
        // Arrange
        GetTaxStatusDto taxStatus1 = new GetTaxStatusDto();
        taxStatus1.setId(1);
        taxStatus1.setDescription("Tax Status 1");

        GetTaxStatusDto taxStatus2 = new GetTaxStatusDto();
        taxStatus2.setId(2);
        taxStatus2.setDescription("Tax Status 2");

        List<GetTaxStatusDto> mockTaxStatuses = List.of(taxStatus1, taxStatus2);

        Mockito.when(ownerService.getTaxStatus()).thenReturn(mockTaxStatuses);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/taxstatus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Tax Status 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Tax Status 2"));
    }

    @Test
    void getOwnerTypes() throws Exception {

        GetOwnerTypeDto ownerType1 = new GetOwnerTypeDto();
        ownerType1.setId(1);
        ownerType1.setDescription("Owner Type 1");

        GetOwnerTypeDto ownerType2 = new GetOwnerTypeDto();
        ownerType2.setId(2);
        ownerType2.setDescription("Owner Type 2");

        List<GetOwnerTypeDto> mockOwnerTypes = List.of(ownerType1, ownerType2);

        Mockito.when(ownerService.getOwnerTypes()).thenReturn(mockOwnerTypes);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/ownertypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Owner Type 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Owner Type 2"));
    }

    @Test
    void getOwners() throws Exception {
        GetOwnerDto owner1 = new GetOwnerDto();
        owner1.setId(1);
        owner1.setName("John Doe");

        GetOwnerDto owner2 = new GetOwnerDto();
        owner2.setId(2);
        owner2.setName("Jane Doe");

        List<GetOwnerDto> mockOwners = List.of(owner1, owner2);

        Mockito.when(ownerService.getAllOwners()).thenReturn(mockOwners);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void getOwnersByPlotId() {
    }

    @Test
    void getOwnersPlots() throws Exception {

    }

    @Test
    void getOwnerAndPlotById() throws Exception {

    }

    @Test
    void deleteOwner() throws Exception {
        Integer ownerId = 1;
        Integer userIdUpdate = 2;

        Mockito.doNothing().when(ownerService).deleteOwner(ownerId, userIdUpdate);

        mockMvc.perform(MockMvcRequestBuilders.delete("/owners/{id}/{userIdUpdate}", ownerId, userIdUpdate))
                .andExpect(status().isNoContent());
    }
}