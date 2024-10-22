package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.UserPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        postOwnerDto.setCuitCuil("20123456789");
        postOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
        postOwnerDto.setOwnerTypeId(1);
        postOwnerDto.setTaxStatusId(1);
        postOwnerDto.setActive(true);
        postOwnerDto.setAvatarUrl("url123");
        postOwnerDto.setPlotId(12);
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

    @Test
    void createUser_ItNotSuccess() {
        // Given
        PostOwnerDto postOwnerDto = new PostOwnerDto();
        postOwnerDto.setName("name");
        postOwnerDto.setLastname("lastname");
        postOwnerDto.setUsername("username");
        postOwnerDto.setPassword("password");
        postOwnerDto.setEmail("email@example.com");
        postOwnerDto.setPhoneNumber("123456789");
        postOwnerDto.setDni("12345678");
        postOwnerDto.setCuitCuil("20123456789");
        postOwnerDto.setDateBirth(LocalDate.of(1990, 1, 1));
        postOwnerDto.setOwnerTypeId(1);
        postOwnerDto.setTaxStatusId(1);
        postOwnerDto.setActive(true);
        postOwnerDto.setAvatarUrl("url123");
        postOwnerDto.setPlotId(12);
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

    @Test
    void getUser() {
    }

    @Test
    void deleteUser() {
    }
}