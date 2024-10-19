package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<GetOwnerDto> postOwner(@RequestBody PostOwnerDto postOwnerDto) {
        GetOwnerDto getOwnerDto = ownerService.createOwner(postOwnerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(getOwnerDto);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<GetOwnerDto> putOwner(@PathVariable Integer ownerId, @RequestBody PutOwnerDto putOwnerDto) {
        GetOwnerDto getOwnerDto = ownerService.updateOwner(ownerId, putOwnerDto);
        return ResponseEntity.ok(getOwnerDto);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<GetOwnerDto> getOwnerById(@PathVariable Integer ownerId){
        GetOwnerDto result = ownerService.getById(ownerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/taxstatus")
    public ResponseEntity<List<GetTaxStatusDto>> getTaxStatus() {
        return ResponseEntity.ok(ownerService.getTaxStatus());
    }

    @GetMapping("/ownertypes")
    public ResponseEntity<List<GetOwnerTypeDto>> getOwnerTypes() {
        return ResponseEntity.ok(ownerService.getOwnerTypes());
    }

    @GetMapping()
    public ResponseEntity<List<OwnerDto>> getOwners(){
        List<OwnerDto> result = ownerService.getAllOwners();

        if(result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/plot/{plotId}")
    public ResponseEntity<List<OwnerDto>> getOwnersByPlotId(@PathVariable Integer plotId) {
        List<OwnerDto> owners = ownerService.getOwnersByPlotId(plotId);
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/ownersandplots")
    public ResponseEntity<List<GetOwnerAndPlot>> getOwnersPlots() {
        return ResponseEntity.ok(ownerService.getOwersAndPlots());
    }

    @GetMapping("/ownersandplots/{ownerId}")
    public ResponseEntity<GetOwnerAndPlot> getOwnerAndPlotById(@PathVariable Integer ownerId) {
        GetOwnerAndPlot ownerAndPlot = ownerService.getOwnerAndPlotById(ownerId);
        if (ownerAndPlot == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerAndPlot);
    }

    @DeleteMapping("/{id}/{userIdUpdate}")
    public ResponseEntity<Void> deleteOwner(@PathVariable("id") Integer ownerId, @PathVariable("userIdUpdate") Integer userIdUpdate) {
        ownerService.deleteOwner(ownerId, userIdUpdate);
        return ResponseEntity.noContent().build();
    }
}
