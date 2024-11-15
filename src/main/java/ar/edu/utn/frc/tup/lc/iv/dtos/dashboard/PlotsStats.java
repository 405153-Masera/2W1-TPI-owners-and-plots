package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsStats {

    private long totalPlots;
    private long availablePlots;
    private long constructionPlots;
    private long occupiedPlots;
    private double totalArea;
    private double constructedArea;
}
