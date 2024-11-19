package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class VerificationServiceImplTest {

    private VerificationServiceImpl verificationService;

    @Mock
    private PlotRepository plotRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        verificationService = new VerificationServiceImpl(plotRepository);
    }

    @Test
    void isPlotNumberUnique_ShouldReturnTrue_WhenPlotNumberDoesNotExist() {
        Integer plotNumber = 123;
        when(plotRepository.existsByPlotNumber(plotNumber)).thenReturn(false);

        boolean result = verificationService.isPlotNumberUnique(plotNumber);

        assertTrue(result, "El número de lote debería ser único.");
    }

    @Test
    void isPlotNumberUnique_ShouldReturnFalse_WhenPlotNumberExists() {
        Integer plotNumber = 456;
        when(plotRepository.existsByPlotNumber(plotNumber)).thenReturn(true);

        boolean result = verificationService.isPlotNumberUnique(plotNumber);

        assertFalse(result, "El número de lote no debería ser único.");
    }
}
