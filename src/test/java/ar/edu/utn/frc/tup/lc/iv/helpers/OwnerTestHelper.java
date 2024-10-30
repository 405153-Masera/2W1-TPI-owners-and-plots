package ar.edu.utn.frc.tup.lc.iv.helpers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerAndPlot;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetPlotDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.OwnerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.*;
import ar.edu.utn.frc.tup.lc.iv.restTemplate.users.GetUserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OwnerTestHelper {

    public static final OwnerDto OWNER_DTO = OwnerDto.builder()
            .id(2)
            .name("Manu")
            .lastname("Ginóbili")
            .dni("45591511")
            .cuitCuil("20-22334455-9")
            .dateBirth(LocalDate.of(1977, 7, 28))
            .businessName("Ginóbili Enterprises")
            .active(true)
            .ownerType("Fisica")
            .build();


    public static final GetPlotDto GET_PLOT_DTO_1 = GetPlotDto.builder()
            .id(2)
            .plot_number(202)
            .block_number(20)
            .total_area_in_m2(300.0)
            .built_area_in_m2(180.0)
            .plot_state("Disponible")
            .plot_type("Casa")
            .files(new ArrayList<>())
            .build();

    public static final GetUserDto GET_USER_DTO = GetUserDto.builder()
            .id(2)
            .name("Manu")
            .lastname("Ginóbili")
            .username("manuginobili20")
            .password("GinobiliRocks#20")
            .email("manu@gmail.com")
            .phone_number("3541660812")
            .dni("45591511")
            .active(true)
            .avatar_url("https://avatar.com/manu.jpg")
            .datebirth(LocalDate.of(1977, 7, 28))
            .roles(new String[]{"ADMIN"})
            .plot_id(2)
            .telegram_id(2002)
            .build();

    public static final GetOwnerAndPlot GET_OWNER_AND_PLOT_1 = GetOwnerAndPlot.builder()
            .owner(OWNER_DTO)
            //.plot(GET_PLOT_DTO_1)
            .user(GET_USER_DTO)
            .build();


    // Tipos de propietarios
    public static final OwnerTypeEntity OWNER_TYPE_FISICA = OwnerTypeEntity.builder()
            .id(1)
            .description("Persona Física")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final OwnerTypeEntity OWNER_TYPE_JURIDICA = OwnerTypeEntity.builder()
            .id(2)
            .description("Persona Jurídica")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    // Situación fiscal
    public static final TaxStatusEntity TAX_STATUS_RESPONSABLE_INSCRIPTO = TaxStatusEntity.builder()
            .id(1)
            .description("Responsable Inscripto")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final TaxStatusEntity TAX_STATUS_MONOTRIBUTO = TaxStatusEntity.builder()
            .id(2)
            .description("Monotributo")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    // Manu Ginóbili
    public static final OwnerEntity OWNER_ENTITY_1 = OwnerEntity.builder()
            .id(1)
            .name("Manu")
            .lastname("Ginóbili")
            .dni("45591511")
            .cuitCuil("20-22334455-9")
            .dateBirth(LocalDate.of(1977, 7, 28))
            .businessName("Ginóbili Enterprises")
            .active(true)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .ownerType(OWNER_TYPE_FISICA)
            .taxStatus(TAX_STATUS_RESPONSABLE_INSCRIPTO)
            .files(new ArrayList<>())
            .build();

    // Fabricio Oberto
    public static final OwnerEntity OWNER_ENTITY_2 = OwnerEntity.builder()
            .id(2)
            .name("Fabricio")
            .lastname("Oberto")
            .dni("45591512")
            .cuitCuil("20-22334456-8")
            .dateBirth(LocalDate.of(1975, 3, 21))
            .businessName("Oberto Corp")
            .active(true)
            .createdDatetime(LocalDateTime.now())
            .createdUser(2)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(2)
            .ownerType(OWNER_TYPE_FISICA)
            .taxStatus(TAX_STATUS_MONOTRIBUTO)
            .files(new ArrayList<>())
            .build();

    // Luis Scola
    public static final OwnerEntity OWNER_ENTITY_3 = OwnerEntity.builder()
            .id(3)
            .name("Luis")
            .lastname("Scola")
            .dni("45591513")
            .cuitCuil("20-22334457-7")
            .dateBirth(LocalDate.of(1980, 4, 30))
            .businessName("Scola Holdings")
            .active(true)
            .createdDatetime(LocalDateTime.now())
            .createdUser(3)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(3)
            .ownerType(OWNER_TYPE_JURIDICA)
            .taxStatus(TAX_STATUS_RESPONSABLE_INSCRIPTO)
            .files(new ArrayList<>())
            .build();

    // Lista que contiene los tres OwnerEntity
    public static final List<OwnerEntity> OWNER_ENTITY_LIST = Arrays.asList(
            OWNER_ENTITY_1,
            OWNER_ENTITY_2,
            OWNER_ENTITY_3
    );

    public static final PlotStateEntity PLOT_STATE_ENTITY = PlotStateEntity.builder()
            .id(2)
            .name("Vendido")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotTypeEntity PLOT_TYPE_ENTITY = PlotTypeEntity.builder()
            .id(2)
            .name("Negocio")
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotEntity PLOT_ENTITY_1 = PlotEntity.builder()
            .id(1)
            .plotNumber(202)
            .blockNumber(20)
            .totalAreaInM2(300.0)
            .builtAreaInM2(180.0)
            .plotState(PLOT_STATE_ENTITY)
            .plotType(PLOT_TYPE_ENTITY)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotOwnerEntity PLOT_OWNER_ENTITY_1 = PlotOwnerEntity.builder()
            .id(1)
            .plot(PLOT_ENTITY_1)
            .owner(OWNER_ENTITY_1)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotEntity PLOT_ENTITY_2 = PlotEntity.builder()
            .id(2)
            .plotNumber(203)
            .blockNumber(21)
            .totalAreaInM2(320.0)
            .builtAreaInM2(200.0)
            .plotState(PLOT_STATE_ENTITY)
            .plotType(PLOT_TYPE_ENTITY)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotEntity PLOT_ENTITY_3 = PlotEntity.builder()
            .id(3)
            .plotNumber(204)
            .blockNumber(22)
            .totalAreaInM2(350.0)
            .builtAreaInM2(220.0)
            .plotState(PLOT_STATE_ENTITY)
            .plotType(PLOT_TYPE_ENTITY)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotOwnerEntity PLOT_OWNER_ENTITY_2 = PlotOwnerEntity.builder()
            .id(2)
            .plot(PLOT_ENTITY_2)
            .owner(OWNER_ENTITY_2)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

    public static final PlotOwnerEntity PLOT_OWNER_ENTITY_3 = PlotOwnerEntity.builder()
            .id(3)
            .plot(PLOT_ENTITY_3)
            .owner(OWNER_ENTITY_3)
            .createdDatetime(LocalDateTime.now())
            .createdUser(1)
            .lastUpdatedDatetime(LocalDateTime.now())
            .lastUpdatedUser(1)
            .build();

}
