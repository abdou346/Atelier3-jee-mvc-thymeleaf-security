package ma.emsi.patientmvc.sec.repositories;

import ma.emsi.patientmvc.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository <AppUser,String>{
    AppUser findByUsername(String username);


}
