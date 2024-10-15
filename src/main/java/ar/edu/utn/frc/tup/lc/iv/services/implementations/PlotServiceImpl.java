package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlotServiceImpl implements PlotService {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GetPlotDto createPlot(PostPlotDto postPlotDto) {
        return null;
    }

    @Override
    public GetPlotStateDto getPlotStates() {
        return null;
    }

    @Override
    public GetPlotTypeDto getPlotTypes() {
        return null;
    }
}
