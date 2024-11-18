package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import org.springframework.stereotype.Service;

/**
 * Interfaz que contiene logica de verificación.
 */
@Service
public interface VerificationService {

    /**
     * Metodo que valida si el número de lote es único.
     * @param plotNumber numero de lote.
     * @return true si el número de lote es único, false en caso contrario.
     */
    boolean isPlotNumberUnique(Integer plotNumber);

}
