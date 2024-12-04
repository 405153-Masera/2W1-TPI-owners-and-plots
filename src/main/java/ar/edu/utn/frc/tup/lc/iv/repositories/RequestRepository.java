package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.DniTypeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
}
