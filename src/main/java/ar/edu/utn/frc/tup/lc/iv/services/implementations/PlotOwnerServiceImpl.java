package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotOwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotOwnerEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotOwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotOwnerService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class PlotOwnerServiceImpl implements PlotOwnerService {

    /**
     * Repositorio para manejar la entidad PlotOwnerEntity.
     */
    private final PlotOwnerRepository plotOwnerRepository;

    /**
     * Devuelve una lista de GetPlotOwnerDto que contiene los identificadores
     * de los propietarios y los lotes.
     *
     * @return una lista de GetPlotOwnerDto.
     */
    @Override
    public List<GetPlotOwnerDto> getAllPlotOwner() {
        List<PlotOwnerEntity> plotOwnerEntities = plotOwnerRepository.findAll();
        return plotOwnerEntities.stream()
                .map(POE -> {
                    GetPlotOwnerDto plotOwnerDto = new GetPlotOwnerDto();
                    plotOwnerDto.setPlot_id(POE.getPlot().getId());
                    plotOwnerDto.setOwner_id(POE.getOwner().getId());
                    return plotOwnerDto;
                })
                .collect(Collectors.toList());
    }
}
