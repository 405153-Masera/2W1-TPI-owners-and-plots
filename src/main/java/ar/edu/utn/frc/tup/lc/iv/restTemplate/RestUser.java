package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.UserPost;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Servicio que se encarga de realizar las peticiones REST a la API de usuarios.
 */
@Service
@AllArgsConstructor
public class RestUser {

    private final RestTemplate restTemplate;
    private String url = "http://localhost:8080/users";

    @Autowired
    public RestUser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean createUser(PostOwnerDto postOwnerDto) {

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

        try {

            ResponseEntity<Void> response = restTemplate.postForEntity(url, userPost, Void.class);
            return response.getStatusCode().is2xxSuccessful();

        }catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }

    public GetUserDto getUser(Integer plotId) {

        ResponseEntity<GetUserDto> response = restTemplate.getForEntity(url + "/plot/" + plotId, GetUserDto.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }else {
            throw  new EntityNotFoundException("No se encontró el usuario");
        }

    }

    public void deleteUser(Integer userId, Integer userIdUpdate) {
        try{
            restTemplate.delete(url + "/" + userId + "/" + userIdUpdate);
        }catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }
}
