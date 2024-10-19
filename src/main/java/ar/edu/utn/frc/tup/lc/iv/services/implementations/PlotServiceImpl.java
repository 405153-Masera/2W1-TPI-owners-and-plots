package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotStateDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutPlotDto;
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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Crea un nuevo lote.
     *
     * @param postPlotDto datos del lote a guardar.
     * @return el lote creado.
     */
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
        mapPlotEntityToGetPlotDto(savedPlot, getPlotDto);

        //Retornamos el getPlotDto
        return getPlotDto;

    }

    /**
     * Obtiene los estados de los lotes.
     *
     * @return lista de estados de los lotes.
     */
    @Override
    public List<GetPlotStateDto> getPlotStates() {
        List<PlotStateEntity> plotStateEntities = plotStateRepository.findAll();
        List<GetPlotStateDto> plotStateDtos = new ArrayList<>();
        for (PlotStateEntity plotStateEntity : plotStateEntities) {
            plotStateDtos.add(modelMapper.map(plotStateEntity, GetPlotStateDto.class));
        }
        return plotStateDtos;
    }

    /**
     * Obtiene los tipos de los lotes.
     *
     * @return lista de tipos de los lotes.
     */
    @Override
    public List<GetPlotTypeDto> getPlotTypes() {
        List<PlotTypeEntity> plotTypeEntities = plotTypeRepository.findAll();
        List<GetPlotTypeDto> plotTypeDtos = new ArrayList<>();
        for (PlotTypeEntity plotTypeEntity : plotTypeEntities) {
            plotTypeDtos.add(modelMapper.map(plotTypeEntity, GetPlotTypeDto.class));
        }
        return plotTypeDtos;
    }

    /**
     * Actualiza un lote.
     *
     * @param plotDto datos del lote a actualizar.
     * @param plotId  id del lote a actualizar.
     * @throws RuntimeException si el lote no existe, si el estado y tipo del lote no existen.
     * @return el lote actualizado.
     */
    @Override
    @Transactional
    //todo SALE ESE ERROR RARO
    //todo El error que estás viendo en tu operación PUT está relacionado con una limitación en MySQL que evita la
    // modificación de una tabla que ya está siendo utilizada en un trigger
    public GetPlotDto putPlot(PutPlotDto plotDto, Integer plotId) {
        PlotEntity plotEntity = this.plotRepository.findById(plotId).get();

        if(plotEntity == null){
            throw new RuntimeException();
        }

        PlotStateEntity plotStateEntity = this.plotStateRepository.findById(plotDto.getPlot_state_id()).get();
        if(plotStateEntity == null){
            throw new RuntimeException();
        }

        PlotTypeEntity plotTypeEntity = this.plotTypeRepository.findById(plotDto.getPlot_type_id()).get();
        if(plotTypeEntity == null){
            throw new RuntimeException();
        }

        plotEntity.setTotalAreaInM2(plotDto.getTotal_area_in_m2());
        plotEntity.setBuiltAreaInM2(plotDto.getBuilt_area_in_m2());
        plotEntity.setPlotState(plotStateEntity);
        plotEntity.setPlotType(plotTypeEntity);
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedUser(plotDto.getUserUpdateId());
        plotEntity = this.plotRepository.save(plotEntity);
        GetPlotDto getPlotDto = new GetPlotDto();
        this.mapPlotEntityToGetPlotDto(plotEntity , getPlotDto);
        return getPlotDto;
    }

    /**
     * Obtiene todos los lotes.
     *
     * @return una lista con todos los lotes.
     */
    @Override
    public List<GetPlotDto> getAllPlots() {
        List<PlotEntity> plotEntities = plotRepository.findAll();
        List<GetPlotDto> plotDtos = new ArrayList<>();
        for (PlotEntity plotEntity : plotEntities) {
            GetPlotDto getPlotDto = new GetPlotDto();
            mapPlotEntityToGetPlotDto(plotEntity, getPlotDto );
            plotDtos.add(getPlotDto);
        }
        return plotDtos;
    }

    /**
     * Obtiene todos los lotes disponibles.
     *
     * @return una lista con todos los lotes disponibles.
     */
    @Override
    public List<GetPlotDto> getAllPlotsAvailables() {
        List<PlotEntity> plotEntities = plotRepository.findPlotsAvailables();
        List<GetPlotDto> plotDtos = new ArrayList<>();
        for (PlotEntity plotEntity : plotEntities) { GetPlotDto getPlotDto = new GetPlotDto();
            mapPlotEntityToGetPlotDto(plotEntity, getPlotDto );
            plotDtos.add(getPlotDto);
        }
        return plotDtos;
    }

    /**
     * Obtiene un lote por su id.
     *
     * @param plotId id del lote a buscar.
     * @throws EntityNotFoundException si el lote no existe.
     * @return el lote encontrado.
     */
    public GetPlotDto getPlotById(Integer plotId) {
        PlotEntity plotEntity = plotRepository.findById(plotId)
                .orElseThrow(() -> new EntityNotFoundException("Plot not found with id: " + plotId));
        GetPlotDto getPlotDto = new GetPlotDto();
        mapPlotEntityToGetPlotDto(plotEntity, getPlotDto);
        return getPlotDto;
    }

    /**
     * Valida si un lote existe con el número de lote pasado.
     *
     * @param plotNumber número de lote a validar.
     * @throws IllegalArgumentException si el lote ya existe.
     */
    public void validatePlotNumber(Integer plotNumber) {
        if (plotRepository.findByPlotNumber(plotNumber) != null) {
            throw new IllegalArgumentException("Error creating plot: Plot already exist.");
        }
    }

    /**
     * Mapea los datos de un lote a una entidad de lote y la guarda en la base de datos.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param postPlotDto datos del lote a mapear.
     * @throws EntityNotFoundException si el estado o tipo del lote no existen.
     */
    public void mapPlotPostToPlotEntity(PlotEntity plotEntity, PostPlotDto postPlotDto) {
        //Seteamos campos basicos
        plotEntity.setPlotNumber(postPlotDto.getPlot_number());
        plotEntity.setBlockNumber(postPlotDto.getBlock_number());
        plotEntity.setBuiltAreaInM2(postPlotDto.getBuilt_area_in_m2());
        plotEntity.setTotalAreaInM2(postPlotDto.getTotal_area_in_m2());
        plotEntity.setCreatedDatetime(LocalDateTime.now());
        plotEntity.setLastUpdatedDatetime(LocalDateTime.now());
        plotEntity.setCreatedUser(postPlotDto.getUserCreateId());
        plotEntity.setLastUpdatedUser(plotEntity.getLastUpdatedUser());

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

    /**
     * Mapea los datos de un lote a un dto de lote.
     *
     * @param plotEntity entidad de lote a mapear.
     * @param getPlotDto dto de lote a mapear.
     */
    @Override
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
