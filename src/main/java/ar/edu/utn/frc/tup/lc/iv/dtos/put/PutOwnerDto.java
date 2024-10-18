package ar.edu.utn.frc.tup.lc.iv.dtos.put;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutOwnerDto {


    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 50, message = "Lastname must be between 1 and 50 characters")
    private String lastname;

    @NotBlank(message = "DNI cannot be blank")
    private String dni;

    @NotBlank(message = "CUIT/CUIL cannot be blank")
    private String cuitCuil;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d+", message = "Phone number must be numeric")
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateBirth;

    @NotNull(message = "Owner type cannot be null")
    private Integer ownerTypeId;

    @NotNull(message = "Tax status cannot be null")
    private Integer taxStatusId;

    @Size(min = 1, max = 50, message = "Business name must be between 1 and 50 characters")
    private String businessName;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;


    @NotNull(message = "User update ID cannot be null")
    private Integer userUpdateId;
}
