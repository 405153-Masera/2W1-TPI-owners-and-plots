package ar.edu.utn.frc.tup.lc.iv.dtos.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlotsByBlock {

    private long blockNumber;
    private long occupied;
    private long available;
    private long inConstruction;
    private long total;
}
