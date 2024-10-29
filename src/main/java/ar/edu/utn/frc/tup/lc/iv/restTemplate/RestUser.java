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

    /**
     * Instancia de restTemplate para utilizar dentro de la clase.
     */
    private final RestTemplate restTemplate;

    /**
     * Direccion url donde se levanta el microservicio de usuarios.
     */
    private String url = "http://localhost:9060/users";

    /**
     * Constructor de la clase.
     *
     * @param restTemplate instancia de restTemplate.
     */
    @Autowired
    public RestUser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Metodo para crear un usuario.
     *
     * @param postOwnerDto DTO con la información del usuario a crear.
     * @return true si se creo correctamente, false en caso contrario.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
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
        userPost.setDni_type_id(1);

        try {

            ResponseEntity<Void> response = restTemplate.postForEntity(url + "/post/owner", userPost, Void.class);
            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }

    /**
     * Metodo para obtener un usuario a través de un lote.
     *
     * @param plotId identificador del lote.
     * @return un DTO con la información del usuario.
     * @throws EntityNotFoundException si no se encontro el usuario.
     */
    public GetUserDto getUser(Integer plotId) {

        ResponseEntity<GetUserDto> response = restTemplate.getForEntity(url + "/get/owner/" + plotId, GetUserDto.class);

        System.out.println(response.getBody());

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw  new EntityNotFoundException("No se encontró el usuario");
        }

    }

    /**
     * Metodo para dar una baja lógica a un usuario.
     *
     * @param userId identificador del usuario a dar de baja.
     * @param userIdUpdate identificador del usuario que realiza la baja.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
    public void deleteUser(Integer userId, Integer userIdUpdate) {
        try{
            restTemplate.delete(url + "/delete/" + userId + "/" + userIdUpdate);
        }catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user" + e.getMessage());
        }
    }
}
