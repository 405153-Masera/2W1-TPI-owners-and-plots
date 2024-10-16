package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOwnerDto {

    private String name;
    private String lastname;
    private String dni;
    private String cuitCuil;
    private LocalDate dateBirth;
    private Integer ownerTypeId;
    private Integer taxStatusId;
    private String businessName;
    private Boolean active;

    //Datos del user
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatarUrl;
    @NotNull(message = "Los roles no pueden ser nulos")
    private String[] roles ;

    private Integer userCreateId;
}
