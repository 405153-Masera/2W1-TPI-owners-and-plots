package ar.edu.utn.frc.tup.lc.iv.restTemplate;

import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.OwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.UserPost;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.UserPut;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private final OwnerRepository ownerRepository;

    /**
     * Dirección url donde se levanta el microservicio de usuarios.
     */
    @Value("${user.service.url}/users")
    private String url;

    /**
     * Constructor de la clase.
     *
     * @param restTemplate instancia de restTemplate.
     */
    @Autowired
    public RestUser(RestTemplate restTemplate, OwnerRepository ownerRepository) {
        this.restTemplate = restTemplate;
        this.ownerRepository = ownerRepository;
    }

    /**
     * Metodo para crear un usuario.
     *
     * @param postOwnerDto DTO con la información del usuario a crear.
     * @return true si se creo correctamente, false en caso contrario.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
    public boolean createUser(PostOwnerDto postOwnerDto) {

        UserPost userPost = mapToUserPost(postOwnerDto);
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url + "/post/owner", userPost, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user: " + e.getMessage(), e);
        }
    }

    public void createUserPlot(Integer userId, Integer plotId, Integer userIdUpdate) {
        ResponseEntity<GetUserDto[]> response = restTemplate.getForEntity(url + "/byOwner/" + userId, GetUserDto[].class);
        GetUserDto owner = new GetUserDto();
        for (GetUserDto user : response.getBody()) {
            if (Arrays.asList(user.getRoles()).contains("Propietario")) {
                owner = user;
            }
        }
        try {
            restTemplate.postForEntity(url + "/post/userplot/" + owner.getId() + "/" + plotId + "/" + userIdUpdate, null, Void.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while creating the user: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteUserPlot(Integer userId, Integer plotId) {
        ResponseEntity<GetUserDto[]> response = restTemplate.getForEntity(url + "/byOwner/" + userId, GetUserDto[].class);
        GetUserDto owner = new GetUserDto();
        for (GetUserDto user : response.getBody()) {
            if (Arrays.asList(user.getRoles()).contains("Propietario")) {
                owner = user;
            }
        }
        try {
            restTemplate.delete(url + "/delete/userplot/" + owner.getId() + "/" + plotId);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while deleting the user: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para actualizar un usuario.
     *
     * @param putOwnerDto DTO con la información del usuario a actualizar.
     * @return true si se actualizó correctamente, false en caso contrario.
     * @throws ResponseStatusException si hubo un error en la petición.
     */
    public boolean updateUser(PutOwnerDto putOwnerDto) {
        UserPut userPut = mapToUserPut(putOwnerDto);
        OwnerEntity ownerEntity = ownerRepository.findByDni(putOwnerDto.getDni());
        GetUserDto ownerUser = getOwnerUser(ownerEntity.getId());
        GetUserDto getUserDto = getUser(ownerUser.getPlot_id()[0]);

        Integer userId = getUserDto.getId();

        try {
            restTemplate.put(url + "/put/owner/" + userId, userPut);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while updating the user: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para obtener un usuario a través de un lote.
     *
     * @param plotId identificador del lote.
     * @return un DTO con la información del usuario.
     * @throws EntityNotFoundException si no se encuentra el usuario.
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

    public GetUserDto getOwnerUser(Integer userId) {
        ResponseEntity<GetUserDto[]> response = restTemplate.getForEntity(url + "/byOwner/" + userId, GetUserDto[].class);
        GetUserDto owner = new GetUserDto();
        for (GetUserDto user : response.getBody()) {
            if (Arrays.asList(user.getRoles()).contains("Propietario")) {
                owner = user;
            }
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            return owner;
        } else {
            throw new EntityNotFoundException("No se encontró el usuario");
        }
    }

    /**
     * Metodo para dar una baja lógica a un usuario.
     *
     * @param userId identificador del usuario a dar de baja.
     * @param userIdUpdate identificador del usuario que realiza la baja.
     * @throws ResponseStatusException si hubo un error en la petición o no se encuentra
     * el usuario tipo propietario.
     */
    public void deleteUser(Integer userId, Integer userIdUpdate) {
        GetUserDto ownerUser = findOwnerUser(userId);

        if (ownerUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with role 'Propietario' not found");
        }
        try {
            restTemplate.delete(url + "/delete/" + ownerUser.getId() + "/" + userIdUpdate);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Server error while deleting the user: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo para mapear un DTO a un objeto de tipo {@link UserPut}.
     *
     * @param putOwnerDto DTO con la información del usuario.
     * @return un objeto de tipo {@link UserPut}.
     */
    private UserPut mapToUserPut(PutOwnerDto putOwnerDto) {
        ensureOwnerRole(putOwnerDto);
        UserPut userPut = new UserPut();
        userPut.setName(putOwnerDto.getName());
        userPut.setLastName(putOwnerDto.getLastname());
        userPut.setDni_type_id(putOwnerDto.getDniTypeId());
        userPut.setDni(putOwnerDto.getDni());
        if (putOwnerDto.getPhoneNumber() != null) {
            userPut.setPhoneNumber(putOwnerDto.getPhoneNumber());
        } else {
            userPut.setPhoneNumber(null);
        }
        if (putOwnerDto.getEmail() != null) {
            userPut.setEmail(putOwnerDto.getEmail());
        } else {
            userPut.setEmail(null);
        }
        if (putOwnerDto.getDateBirth() != null) {
            userPut.setDatebirth(putOwnerDto.getDateBirth());
        } else {
            userPut.setDatebirth(null);
        }
        userPut.setRoles(putOwnerDto.getRoles());
        if (putOwnerDto.getTelegram_id() != null) {
            userPut.setTelegram_id(putOwnerDto.getTelegram_id());
        } else {
            userPut.setTelegram_id(null);
        }
        userPut.setUserUpdateId(putOwnerDto.getUserUpdateId());
        userPut.setPlot_id(putOwnerDto.getPlotId());
        return userPut;
    }

    /**
     * Metodo para mapear un DTO a un objeto de tipo {@link UserPost}.
     *
     * @param postOwnerDto DTO con la información del usuario.
     * @return un objeto de tipo {@link UserPost}.
     */
    private UserPost mapToUserPost(PostOwnerDto postOwnerDto) {
        ensureOwnerRole(postOwnerDto);
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
        userPost.setDni_type_id(postOwnerDto.getDni_type_id());
        return userPost;
    }

    /**
     * Metodo para asegurar que el usuario tenga el rol de propietario.
     *
     * @param postOwnerDto DTO con la información del usuario.
     */
    private void ensureOwnerRole(PostOwnerDto postOwnerDto) {
        String[] roles = postOwnerDto.getRoles();

        List<String> rolesList = roles != null ? new ArrayList<>(List.of(roles)) : new ArrayList<>();

        if (!rolesList.contains("Propietario")) {
            rolesList.add("Propietario");
        }

        postOwnerDto.setRoles(rolesList.toArray(new String[0]));
    }

    /**
     * Metodo para asegurar que el usuario tenga el rol de propietario.
     *
     * @param putOwnerDto DTO con la información del usuario.
     */
    private void ensureOwnerRole(PutOwnerDto putOwnerDto) {
        String[] roles = putOwnerDto.getRoles();

        List<String> rolesList = roles != null ? new ArrayList<>(List.of(roles)) : new ArrayList<>();

        if (!rolesList.contains("Propietario")) {
            rolesList.add("Propietario");
        }
        putOwnerDto.setRoles(rolesList.toArray(new String[0]));
    }


    /**
     * Metodo para obtener un usuario propietario.
     *
     * @param userId identificador del usuario.
     * @return un DTO con la información del usuario propietario.
     */
    public GetUserDto findOwnerUser(Integer userId) {
        String endpoint = String.format("%s/byOwner/%d", url, userId);
        ResponseEntity<List<GetUserDto>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GetUserDto>>() { }
        );
        List<GetUserDto> users = response.getBody();
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.stream()
                .filter(user -> user.getRoles() != null && Arrays.asList(user.getRoles()).contains("Propietario"))
                .findFirst()
                .orElse(null);
    }
}
