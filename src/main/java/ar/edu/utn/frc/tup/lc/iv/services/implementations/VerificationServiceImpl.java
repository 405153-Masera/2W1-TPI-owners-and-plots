package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz VerificationService,
 * contiene la lógica de verificación.
 */
@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    /**
     * Repositorio para manejar los datos de los lotes.
     */
    private final PlotRepository plotRepository;

    /**
     * Metodo que valida si el número de lote es único.
     * @param plotNumber numero de lote.
     */
    @Override
    public boolean isPlotNumberUnique(Integer plotNumber) {
        return !plotRepository.existsByPlotNumber(plotNumber);
    }
}
