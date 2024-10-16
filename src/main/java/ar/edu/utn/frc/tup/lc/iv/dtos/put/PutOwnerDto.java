package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutOwnerDto {
    private String name;
    private String lastname;
    private String dni;
    private String cuitCuil;
    private String email;
    private String phoneNumber;
    private LocalDate dateBirth;
    private Integer ownerTypeId;
    private Integer taxStatusId;
    private String businessName;
    private Boolean active;

    private Integer userUpdateId;
}
