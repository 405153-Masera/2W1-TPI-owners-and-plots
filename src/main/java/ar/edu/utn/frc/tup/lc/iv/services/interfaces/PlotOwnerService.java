package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlotOwnerService {
    List<GetPlotOwnerDto> getAllPlotOwner();
}
