package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlotOwnerService {

    /**
     * Devuelve una lista de GetPlotOwnerDto que contiene los identificadores
     * de los propietarios y los lotes.
     *
     * @return una lista de GetPlotOwnerDto.
     */
    List<GetPlotOwnerDto> getAllPlotOwner();
}
