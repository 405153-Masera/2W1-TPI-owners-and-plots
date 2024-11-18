package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Interfaz que contiene logica de verificación.
 */
@Service
public interface VerificationService {

    /**
     * Metodo que valida si el número de lote es único.
     * @param plotNumber numero de lote.
     */
    boolean isPlotNumberUnique(Integer plotNumber);

}
