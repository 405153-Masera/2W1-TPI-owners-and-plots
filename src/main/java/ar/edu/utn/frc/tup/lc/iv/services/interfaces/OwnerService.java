package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetOwnerTypeDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetTaxStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OwnerService {
    List<GetTaxStatusDto> getTaxStatus();
    List<GetOwnerTypeDto> getOwnerTypes();
}
