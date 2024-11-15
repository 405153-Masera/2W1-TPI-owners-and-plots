package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa una respuesta que contiene la información de
 * un propietario, y su lote.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOwnerWithHisPlots {

    /**
     * Datos del propietario.
     */
    private OwnerDto owner;

    /**
     * Datos del lote del propietario.
     */
    private List<Integer> plot;

}
