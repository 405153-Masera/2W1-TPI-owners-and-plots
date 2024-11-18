package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.interfaces.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador Rest para la verificación de datos únicos.
 * Se encarga de verificar si los datos ingresados son únicos en la base de datos.
 */
@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    /**
     * Servicio de verificación de datos unicos.
     */
    private final VerificationService verificationService;

    /**
     * Verifica si el número de lote es único.
     *
     * @param plotNumber número de lote.
     * @return Respuesta con el resultado de la verificación.
     */
    @GetMapping("/plotnumber")
    public ResponseEntity<Map<String, Boolean>> isPlotNumberUnique(@RequestParam int plotNumber) {
        boolean isUnique = verificationService.isPlotNumberUnique(plotNumber);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }
}
