package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.UserPost;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestUserTest {

    @MockBean
    RestTemplate restTemplate;

    @SpyBean
    RestUser restUser;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser_ItSuccess() {
        // Given
        PostOwnerDto postOwnerDto = new PostOwnerDto();
        postOwnerDto.setName("name");
        postOwnerDto.setLastname("lastname");
        postOwnerDto.setUsername("username");
        postOwnerDto.setPassword("password");
        postOwnerDto.setEmail("email@example.com");
        postOwnerDto.setPhoneNumber("123456789");
        postOwnerDto.setDni("12345678");
        postOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
        postOwnerDto.setOwnerTypeId(1);
        postOwnerDto.setTaxStatusId(1);
        postOwnerDto.setActive(true);
        postOwnerDto.setAvatarUrl("url123");
        //postOwnerDto.setPlotId(12);
        postOwnerDto.setTelegramId(12);
        postOwnerDto.setUserCreateId(12);

        UserPost userPost = new UserPost();
        userPost.setName(postOwnerDto.getName());
        userPost.setLastname(postOwnerDto.getLastname());
        userPost.setUsername(postOwnerDto.getUsername());
        userPost.setPassword(postOwnerDto.getPassword());
        userPost.setEmail(postOwnerDto.getEmail());
        userPost.setPhone_number(postOwnerDto.getPhoneNumber());
        userPost.setDni(postOwnerDto.getDni());
        userPost.setActive(true);
        userPost.setAvatar_url(postOwnerDto.getAvatarUrl());
        userPost.setRoles(postOwnerDto.getRoles());
        userPost.setDatebirth(postOwnerDto.getDateBirth());
        userPost.setUserUpdateId(postOwnerDto.getUserCreateId());
        userPost.setPlot_id(postOwnerDto.getPlotId());
        userPost.setTelegram_id(postOwnerDto.getTelegramId());

        // Mock response
        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.CREATED);

        // When
        when(restTemplate.postForEntity("http://localhost:8080/users", userPost, Void.class))
                .thenReturn(mockResponse);

        // Then
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/users", userPost, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "The user should be created successfully");
        verify(restTemplate, times(1)).postForEntity("http://localhost:8080/users", userPost, Void.class);

    }

//    @Test
//    void createUser_ItNotSuccess() {
//        // Given
//        PostOwnerDto postOwnerDto = new PostOwnerDto();
//        postOwnerDto.setName("name");
//        postOwnerDto.setLastname("lastname");
//        postOwnerDto.setUsername("username");
//        postOwnerDto.setPassword("password");
//        postOwnerDto.setEmail("email@example.com");
//        postOwnerDto.setPhoneNumber("123456789");
//        postOwnerDto.setDni("12345678");
//        postOwnerDto.setCuitCuil("20123456789");
//        postOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
//        postOwnerDto.setOwnerTypeId(1);
//        postOwnerDto.setTaxStatusId(1);
//        postOwnerDto.setActive(true);
//        postOwnerDto.setAvatarUrl("url123");
//        //postOwnerDto.setPlotId(12);
//        postOwnerDto.setTelegramId(12);
//        postOwnerDto.setUserCreateId(12);
//
//        UserPost userPost = new UserPost();
//        userPost.setName(postOwnerDto.getName());
//        userPost.setLastname(postOwnerDto.getLastname());
//        userPost.setUsername(postOwnerDto.getUsername());
//        userPost.setPassword(postOwnerDto.getPassword());
//        userPost.setEmail(postOwnerDto.getEmail());
//        userPost.setPhone_number(postOwnerDto.getPhoneNumber());
//        userPost.setDni(postOwnerDto.getDni());
//        userPost.setActive(true);
//        userPost.setAvatar_url(postOwnerDto.getAvatarUrl());
//        userPost.setRoles(postOwnerDto.getRoles());
//        userPost.setDatebirth(postOwnerDto.getDateBirth());
//        userPost.setUserUpdateId(postOwnerDto.getUserCreateId());
//        userPost.setPlot_id(postOwnerDto.getPlotId());
//        userPost.setTelegram_id(postOwnerDto.getTelegramId());
//
//        // Mock response
//        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.CREATED);
//
//        // When
//        when(restTemplate.postForEntity("http://localhost:8080/users", userPost, Void.class))
//                .thenReturn(mockResponse);
//
//        // Then
//        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/users", userPost, Void.class);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(restTemplate, times(1)).postForEntity("http://localhost:8080/users", userPost, Void.class);
//
//    }

    @Test
    void getUser_ItSuccess() {
        // Given
        Integer plotId = 12;
        GetUserDto mockUserDto = new GetUserDto();
        mockUserDto.setId(1);
        mockUserDto.setName("user1");
        mockUserDto.setLastname("lastname1");
        mockUserDto.setUsername("userlast");
        mockUserDto.setEmail("user@last.com");

        // Mock response
        ResponseEntity<GetUserDto> mockResponse = new ResponseEntity<>(mockUserDto, HttpStatus.OK);

        // When
        // Cambia la URL aquí para que coincida con la URL del método real
        when(restTemplate.getForEntity("http://localhost:9060/users/get/owner/" + plotId, GetUserDto.class))
                .thenReturn(mockResponse);

        // Then
        GetUserDto response = restUser.getUser(plotId);
        assertNotNull(response, "The user should be returned");
        assertEquals("user1", response.getName());
        assertEquals("lastname1", response.getLastname());
        verify(restTemplate, times(1)).getForEntity("http://localhost:9060/users/get/owner/" + plotId, GetUserDto.class);
    }

    @Test
    void getUser_UserNotFound() {
        // Arrange
        Integer plotId = 1;

        // Simulamos que el RestTemplate devuelve una respuesta con un código 404
        ResponseEntity<GetUserDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(GetUserDto.class)))
                .thenReturn(responseEntity);

        // Act & Assert
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            restUser.getUser(plotId);
        });

        // Verificamos que la excepción lanzada tiene el mensaje correcto
        Assertions.assertEquals("No se encontró el usuario", exception.getMessage());
    }



    @Test
    void deleteUser_ItSuccess() {
        // Given
        Integer userId = 1;
        Integer userIdUpdate = 2;
        GetUserDto ownerUser = new GetUserDto();
        ownerUser.setId(userId);
        ownerUser.setRoles(new String[]{"Propietario"});

        // Simulamos que findOwnerUser retorna un usuario propietario
        Mockito.doReturn(ownerUser).when(restUser).findOwnerUser(userId);

        // Simulamos que el método delete no lanza excepciones
        doNothing().when(restTemplate).delete(Mockito.anyString());

        // When
        restUser.deleteUser(userId, userIdUpdate);

        // Then
        verify(restTemplate, times(1)).delete("http://localhost:9060/users/delete/" + userId + "/" + userIdUpdate);
    }

//    @Test
//    void deleteUser_ServerError() {
//        // Arrange
//        Integer userId = 1;
//        Integer userIdUpdate = 2;
//        GetUserDto ownerUser = new GetUserDto();
//        ownerUser.setId(1);
//        ownerUser.setRoles(new String[]{"Propietario"});
//
//        // Simulamos que el método findOwnerUser retorna un usuario propietario
//        Mockito.doReturn(ownerUser).when(restUser).findOwnerUser(userId);
//
//        // Simulamos que el RestTemplate lanza una excepción al intentar eliminar
//        Mockito.doThrow(new RuntimeException("Simulated server error"))
//                .when(restTemplate).delete(Mockito.anyString());
//
//        // Act & Assert
//        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
//            restUser.deleteUser(userId, userIdUpdate);
//        });
//
//        Assertions.assertEquals("Server error while deleting the user :Simulated server error", exception.getReason());
//    }

}