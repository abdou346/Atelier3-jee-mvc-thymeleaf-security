package ma.emsi.patientmvc.repositories;

import ma.emsi.patientmvc.entities.Medecin;
import ma.emsi.patientmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {

    Page<Medecin> findByNomContains(String n, Pageable page);
    Page<Medecin> findBySexe(String n,Pageable page);





}
