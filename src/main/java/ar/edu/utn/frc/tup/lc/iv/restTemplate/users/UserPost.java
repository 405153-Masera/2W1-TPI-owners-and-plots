package ar.edu.utn.frc.tup.lc.iv.restTemplate.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPost {

    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String dni;
    private Boolean active;
    private String avatarUrl;
    private LocalDate datebirth;
    private String[] roles ;
    private Integer userUpdateId;
    private Integer plotId;
}
