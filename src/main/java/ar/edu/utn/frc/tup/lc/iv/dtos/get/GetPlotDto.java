package ar.edu.utn.frc.tup.lc.iv.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO que representa una respuesta que contiene la infromación de
 * un lote.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPlotDto {

    /**
     * Identificador único del lote.
     */
    private int id;

    /**
     * Número de lote.
     */
    private int plot_number;

    /**
     * Número de manzana.
     */
    private int block_number;

    /**
     * Metros cuadrados totales del lote.
     */
    private double total_area_in_m2;

    /**
     * Metros cuadrados construidos del lote.
     */
    private double built_area_in_m2;

    /**
     * Estado del lote (disponible, vendido, etc).
     */
    private String plot_state;

    /**
     * Tipo de lote (casa, negocio, etc).
     */
    private String plot_type;

    /**
     * Lista de archivos asociados al lote.
     */
    private List<FileDto> files;
}
