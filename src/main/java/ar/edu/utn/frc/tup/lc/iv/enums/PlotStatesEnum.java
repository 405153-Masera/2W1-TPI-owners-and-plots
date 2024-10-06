package ar.edu.utn.frc.tup.lc.iv.enums;

public enum PlotStatesEnum {
    AVAILABLE("Disponible"),
    SOLD("Vendido"),
    UNDER_CONSTRUCTION("En construcción");

    private final String value;

    PlotStatesEnum(String value){
        this.value = value;
    }
    //Obtengo el nombre en español
    public String getValue(){
        return this.value;
    }
    //Obtengo el valor del id (el nombre en ingles)
    public String getId(){
        return this.name();
    }

    //Obtener el valor del enum enviado desde el front
    //Plot.setPlotState(PlotStatesEnum.valueOf(postPlotStateDto.getId()));
}
