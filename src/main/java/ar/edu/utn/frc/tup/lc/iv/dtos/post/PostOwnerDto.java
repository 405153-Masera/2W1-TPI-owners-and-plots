package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOwnerDto {

    private String name;
    private String surname;
    private String dni;
    private String cuitCuil;
    private LocalDate dateBirth;
    private Integer ownerTypeId;
    private Integer taxStatusId;
    private String businessName;
    private Boolean active;
    private Integer userId;
}
