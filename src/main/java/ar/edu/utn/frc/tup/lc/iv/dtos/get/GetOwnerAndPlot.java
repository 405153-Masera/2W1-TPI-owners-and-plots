package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta que contiene la información de
 * un propietario, su lote y su usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOwnerAndPlot {

    /**
     * Datos del propietario.
     */
    private OwnerDto owner;

    /**
     * Datos del lote del propietario.
     */
    private GetPlotDto plot;

    /**
     * Datos del usuario del propietario.
     */
    private GetUserDto user;
}
