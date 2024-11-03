package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        postOwnerDto.setName("Juan Ramirez");

        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(1);
        getOwnerDto.setName("Juan Ramirez");

        when(ownerService.createOwner(Mockito.any(PostOwnerDto.class))).thenReturn(getOwnerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/owners")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("postOwnerDto", postOwnerDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Ramirez"));
    }

    @Test
    void putOwner() throws Exception {
        Integer ownerId = 1;
        PutOwnerDto putOwnerDto = new PutOwnerDto();
        putOwnerDto.setName("Carlos Updated");

        GetOwnerDto getOwnerDto = new GetOwnerDto();
        getOwnerDto.setId(ownerId);
        getOwnerDto.setName("Carlos Updated");

        when(ownerService.updateOwner(Mockito.eq(ownerId), Mockito.any(PutOwnerDto.class))).thenReturn(getOwnerDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/owners/{ownerId}", ownerId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("putOwnerDto", putOwnerDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ownerId))
                .andExpect(jsonPath("$.name").value("Carlos Updated"));
    }

    @Test
    void getOwnerById() throws Exception {
        GetOwnerDto mockOwner = new GetOwnerDto();
        mockOwner.setId(1);
        mockOwner.setName("Juan Ramirez");

        when(ownerService.getById(anyInt())).thenReturn(mockOwner);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Ramirez"));

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

        when(ownerService.getTaxStatus()).thenReturn(mockTaxStatuses);

        mockMvc.perform(get("/owners/taxstatus"))
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

        when(ownerService.getOwnerTypes()).thenReturn(mockOwnerTypes);

        mockMvc.perform(get("/owners/ownertypes"))
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
        owner1.setName("Carlos ");

        GetOwnerDto owner2 = new GetOwnerDto();
        owner2.setId(2);
        owner2.setName("Mateo");

        List<GetOwnerDto> mockOwners = List.of(owner1, owner2);

        when(ownerService.getAllOwners()).thenReturn(mockOwners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Carlos "))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Mateo"));
    }

    @Test
    void getOwnersByPlotId() throws Exception {
        Integer plotId = 1;

        OwnerDto owner1 = new OwnerDto();
        owner1.setId(1);
        owner1.setName("Carlos");

        OwnerDto owner2 = new OwnerDto();
        owner2.setId(2);
        owner2.setName("Mateo");

        List<OwnerDto> mockOwners = List.of(owner1, owner2);

        when(ownerService.getOwnersByPlotId(plotId)).thenReturn(mockOwners);

        mockMvc.perform(get("/owners/plot/{plotId}", plotId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Carlos"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Mateo"));
    }

    @Test
    public void testGetOwnersPlots() throws Exception {
        GetOwnerAndPlot sampleOwnerAndPlot = GetOwnerAndPlot.builder()
                .owner(OwnerDto.builder()
                        .id(1)
                        .name("Juan")
                        .lastname("Ramirez")
                        .build())
                .plot(Collections.singletonList(GetPlotDto.builder()
                        .id(1)
                        .plot_number(101)
                        .block_number(5)
                        .build()))
                .user(GetUserDto.builder()
                        .id(1)
                        .name("Admin")
                        .username("admin_user")
                        .build())
                .build();

        List<GetOwnerAndPlot> ownersList = Collections.singletonList(sampleOwnerAndPlot);
        when(ownerService.getOwersAndPlots()).thenReturn(ownersList);

        mockMvc.perform(get("/owners/ownersandplots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].owner.name").value("Juan"))
                .andExpect(jsonPath("$[0].plot[0].plot_number").value(101))
                .andExpect(jsonPath("$[0].user.username").value("admin_user"));
    }

    @Test
    public void testGetOwnerAndPlotById() throws Exception {
        GetOwnerAndPlot sampleOwnerAndPlot = GetOwnerAndPlot.builder()
                .owner(OwnerDto.builder()
                        .id(1)
                        .name("Juan")
                        .lastname("Ramirez")
                        .build())
                .plot(Collections.singletonList(GetPlotDto.builder()
                        .id(1)
                        .plot_number(101)
                        .block_number(5)
                        .build()))
                .user(GetUserDto.builder()
                        .id(1)
                        .name("Admin")
                        .username("admin_user")
                        .build())
                .build();

        when(ownerService.getOwnerAndPlotById(1)).thenReturn(sampleOwnerAndPlot);

        mockMvc.perform(get("/owners/ownersandplots/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner.name").value("Juan"))
                .andExpect(jsonPath("$.plot[0].plot_number").value(101))
                .andExpect(jsonPath("$.user.username").value("admin_user"));
    }

    @Test
    public void testGetOwnerAndPlotByIdNotFound() throws Exception {
        when(ownerService.getOwnerAndPlotById(99)).thenReturn(null);

        mockMvc.perform(get("/owners/ownersandplots/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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