package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import ar.edu.utn.frc.tup.lc.iv.models.Owner;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOwnerAndPlot {
    private OwnerDto owner;
    private GetPlotDto plot;
    private GetUserDto user;
}
