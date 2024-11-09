package ar.edu.utn.frc.tup.lc.iv.services.dashboard;

import ar.edu.utn.frc.tup.lc.iv.repositories.OwnerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerStatsService {

    private final OwnerRepository ownerRepository;

    private final PlotRepository plotRepository;
}
