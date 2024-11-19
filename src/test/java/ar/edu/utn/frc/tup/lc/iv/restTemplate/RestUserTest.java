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

        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.CREATED);

        when(restTemplate.postForEntity("http:localhost:8080/users", userPost, Void.class))
                .thenReturn(mockResponse);

        ResponseEntity<Void> response = restTemplate.postForEntity("http:localhost:8080/users", userPost, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "The user should be created successfully");
        verify(restTemplate, times(1)).postForEntity("http:localhost:8080/users", userPost, Void.class);
    }

    @Test
    void createUser_ItNotSuccess() {
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

        ResponseEntity<Void> mockResponse = new ResponseEntity<>(HttpStatus.CREATED);

        when(restTemplate.postForEntity("http:localhost:8080/users", userPost, Void.class))
                .thenReturn(mockResponse);

        ResponseEntity<Void> response = restTemplate.postForEntity("http:localhost:8080/users", userPost, Void.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(restTemplate, times(1)).postForEntity("http:localhost:8080/users", userPost, Void.class);

    }

    @Test
    void getUser_ItSuccess() {
        Integer plotId = 12;
        GetUserDto mockUserDto = new GetUserDto();
        mockUserDto.setId(1);
        mockUserDto.setName("user1");
        mockUserDto.setLastname("lastname1");
        mockUserDto.setUsername("userlast");
        mockUserDto.setEmail("user@last.com");

        ResponseEntity<GetUserDto> mockResponse = new ResponseEntity<>(mockUserDto, HttpStatus.OK);

        when(restTemplate.getForEntity("http://host.docker.internal:9060/users/get/owner/" + plotId, GetUserDto.class))
                .thenReturn(mockResponse);

        GetUserDto response = restUser.getUser(plotId);
        assertNotNull(response, "The user should be returned");
        assertEquals("user1", response.getName());
        assertEquals("lastname1", response.getLastname());
        verify(restTemplate, times(1)).getForEntity("http://host.docker.internal:9060/users/get/owner/" + plotId, GetUserDto.class);
    }

    @Test
    void getUser_UserNotFound() {
        Integer plotId = 1;

        ResponseEntity<GetUserDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(GetUserDto.class)))
                .thenReturn(responseEntity);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            restUser.getUser(plotId);
        });

        Assertions.assertEquals("No se encontró el usuario", exception.getMessage());
    }


    @Test
    void deleteUser_ItSuccess() {
        Integer userId = 1;
        Integer userIdUpdate = 2;
        GetUserDto ownerUser = new GetUserDto();
        ownerUser.setId(userId);
        ownerUser.setRoles(new String[]{"Propietario"});

        Mockito.doReturn(ownerUser).when(restUser).findOwnerUser(userId);

        doNothing().when(restTemplate).delete(Mockito.anyString());
        restUser.deleteUser(userId, userIdUpdate);

        verify(restTemplate, times(1)).delete("http://host.docker.internal:9060/users/delete/" + userId + "/" + userIdUpdate);
    }

    @Test
    void deleteUser_ServerError() {

        Integer userId = 1;
        Integer userIdUpdate = 2;
        GetUserDto ownerUser = new GetUserDto();
        ownerUser.setId(1);
        ownerUser.setRoles(new String[]{"Propietario"});

        Mockito.doReturn(ownerUser).when(restUser).findOwnerUser(userId);

        Mockito.doThrow(new RuntimeException("Simulated server error"))
                .when(restTemplate).delete(Mockito.anyString());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            restUser.deleteUser(userId, userIdUpdate);
        });

        Assertions.assertEquals("Server error while deleting the user: Simulated server error", exception.getReason());
    }
}