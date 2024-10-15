package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plots")
public class PlotController {

    @Autowired
    private PlotService plotService;

    @GetMapping("/states")
    public ResponseEntity<List<GetPlotStateDto>> getPlotStates() {
        return ResponseEntity.ok(plotService.getPlotStates());
    }

    @GetMapping("/types")
    public ResponseEntity<List<GetPlotTypeDto>> getPlotTypes() {
        return ResponseEntity.ok(plotService.getPlotTypes());
    }
}
