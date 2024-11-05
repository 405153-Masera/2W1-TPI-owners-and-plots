package ar.edu.utn.frc.tup.lc.iv.controllers;


import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import java.awt.*;

@RestController
@RequestMapping("/plotOwners")
public class PlotOwnerController {
    @Autowired
    private PlotOwnerService plotOwnerService;
    @GetMapping()
    public ResponseEntity<List<GetPlotOwnerDto>> getUsers() {
        List<GetPlotOwnerDto> result = plotOwnerService.getAllPlotOwner();
        if(result == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
