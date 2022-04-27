package ma.emsi.patientmvc.repositories;

import ma.emsi.patientmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContainsOrCinContains(String n,String m,Pageable page);
    Page<Patient> findByScore(int score, Pageable page);
    Page<Patient> findBySexe(String n,Pageable page);




    //Page<Patient> findByScoreContains(int n , Pageable pageable) ;
//Page<Patient> findByIdContains(Long n, Pageable pageable) ;
//Page <Patient>findByNomContainsIdContains(String a,int b,Pageable page);
//Page <Patient>findByNomContains(String a,Pageable page)

}
