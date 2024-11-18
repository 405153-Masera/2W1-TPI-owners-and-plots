package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la distribución de los lotes por propietario.
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
     * Cantidad de lotes.
     */
    private long plotCount;

    /**
     * Área total.
     */
    private double totalArea;
}
