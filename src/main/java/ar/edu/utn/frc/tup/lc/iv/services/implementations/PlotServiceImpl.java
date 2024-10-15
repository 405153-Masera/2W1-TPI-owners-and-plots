package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotStateEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotStateRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlotServiceImpl implements PlotService {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private PlotStateRepository plotStateRepository;

    @Autowired
    private PlotTypeRepository plotTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GetPlotDto createPlot(PostPlotDto postPlotDto) {
        return null;
    }

    @Override
    public List<GetPlotStateDto> getPlotStates() {
        List<PlotStateEntity> plotStateEntities = plotStateRepository.findAll();
        List<GetPlotStateDto> plotStateDtos = new ArrayList<>();
        for (PlotStateEntity plotStateEntity : plotStateEntities) {
            plotStateDtos.add(modelMapper.map(plotStateEntity, GetPlotStateDto.class));
        }
        return plotStateDtos;
    }

    @Override
    public List<GetPlotTypeDto> getPlotTypes() {
        List<PlotTypeEntity> plotTypeEntities = plotTypeRepository.findAll();
        List<GetPlotTypeDto> plotTypeDtos = new ArrayList<>();
        for (PlotTypeEntity plotTypeEntity : plotTypeEntities) {
            plotTypeDtos.add(modelMapper.map(plotTypeEntity, GetPlotTypeDto.class));
        }
        return plotTypeDtos;
    }
}
