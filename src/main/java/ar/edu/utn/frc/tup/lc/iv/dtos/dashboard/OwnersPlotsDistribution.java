package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Representa la distribución de terrenos asociados a un propietario.
 * Incluye el nombre del propietario, la cantidad de terrenos y el área total.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnersPlotsDistribution {

    /**
     * Nombre del propietario.
     */
    private String ownerName;

    /**
     * Cantidad de terrenos asociados al propietario.
     */
    private long plotCount;

    /**
     * Área total de los terrenos asociados al propietario.
     */
    private double totalArea;
}
