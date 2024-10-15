package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotStateEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PlotTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotStateRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotTypeRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PlotService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        //Validamos si el el plot a crear existe (mismo numero de plot)
        validatePlotNumber(postPlotDto.getPlot_number());
        //Creamos la entity
        PlotEntity plotEntity = new PlotEntity();
        //Mapeamos con metodo
        mapPlotPostToPlotEntity(plotEntity, postPlotDto);
        //Guardamos el plot
        PlotEntity savedPlot = plotRepository.save(plotEntity);
        //Mapeamos el entity al GetPlotDto para poder mostrarlo
        GetPlotDto getPlotDto = new GetPlotDto();
        mapPlotEntityToGetPlotDto(savedPlot , getPlotDto );

        //Retornamos el getPlotDto
        return getPlotDto;

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

    //Metodo para validar si existe un plot con ese numero
    public void validatePlotNumber(Integer plotNumber) {
        if (plotRepository.findByPlotNumber(plotNumber) != null) {
            throw new IllegalArgumentException("Error creating plot: Plot already exist.");
        }
    }

    public void mapPlotPostToPlotEntity(PlotEntity plotEntity, PostPlotDto postPlotDto) {
        //Seteamos campos basicos
        plotEntity.setPlotNumber(postPlotDto.getPlot_number());
        plotEntity.setBlockNumber(postPlotDto.getBlock_number());
        plotEntity.setBuiltAreaInM2(postPlotDto.getBuilt_area_in_m2());
        plotEntity.setTotalAreaInM2(postPlotDto.getTotal_area_in_m2());
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setCreatedUser(1);
        plotEntity.setLastUpdatedUser(1);

        //Mapeamos los estados y tipos
        PlotStateEntity state = plotStateRepository.findById(postPlotDto.getPlot_state_id())
                .orElseThrow(() -> new EntityNotFoundException("PlotState not found with id: " + postPlotDto.getPlot_state_id()));
        PlotTypeEntity type = plotTypeRepository.findById(postPlotDto.getPlot_type_id())
                .orElseThrow(() -> new EntityNotFoundException("PlotType not found with id: " + postPlotDto.getPlot_type_id()));

        //Seteamos estados y tipos
        plotEntity.setPlotType(type);
        plotEntity.setPlotState(state);

        //Guardamos en el repositorio
        plotRepository.save(plotEntity);
    }

    public void mapPlotEntityToGetPlotDto(PlotEntity plotEntity, GetPlotDto getPlotDto) {
        getPlotDto.setPlot_number(plotEntity.getPlotNumber());
        getPlotDto.setBlock_number(plotEntity.getBlockNumber());
        getPlotDto.setBuilt_area_in_m2(plotEntity.getBuiltAreaInM2());
        getPlotDto.setTotal_area_in_m2(plotEntity.getTotalAreaInM2());
        getPlotDto.setId(plotEntity.getId());
        getPlotDto.setPlot_state(plotEntity.getPlotState().getName());
        getPlotDto.setPlot_type(plotEntity.getPlotType().getName());
    }

}
