package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    private Integer id;
    private String name;
    private String surname;
    private Integer dni;
    private Integer cuitCuil;
    private LocalDate dateBirth;
    private Integer tax_status_id;
    private String bussines_name;
    private Boolean active;
    private LocalDate created_date;
    private LocalDate las_updated_date;
    private Integer created_user;
}
