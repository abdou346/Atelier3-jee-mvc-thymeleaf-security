package ma.emsi.patientmvc;


import ma.emsi.patientmvc.entities.Medecin;
import ma.emsi.patientmvc.entities.Patient;
import ma.emsi.patientmvc.repositories.MedecinRepository;
import ma.emsi.patientmvc.repositories.PatientRepository;
import ma.emsi.patientmvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner commandLineRunnerpatient(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "M","abdou ", new Date(), false, 185,"BE871664"));
            patientRepository.save(new Patient(null,"M", "Hamza ", new Date(), true, 148,"AW871664"));
            patientRepository.save(new Patient(null, "F","fatima ", new Date(), false, 966,"BE871964"));
            patientRepository.save(new Patient(null, "F","Manal", new Date(), true, 164,"BE071664"));
            patientRepository.findAll().forEach(p ->
            {
                System.out.println(p.getNom());
            });
        };
    }
  @Bean
    CommandLineRunner commandLineRunnerMedecin(MedecinRepository medecinRepository) {
        return args -> {
            medecinRepository.save(new Medecin(null, "M","Mehdi", new Date(), "chirurgien"));
            medecinRepository.save(new Medecin(null, "M","Farouk ", new Date(), "Dentiste"));
            medecinRepository.save(new Medecin(null, "F","Youssra ", new Date(), "Cardio"));
            medecinRepository.save(new Medecin(null, "F","Ayaa", new Date(), "Oncologue"));

            medecinRepository.findAll().forEach(p ->
            {
                System.out.println(p.getNom());
            });
        };
    }

   //@Bean
    CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("abdelhadi elkhattabi","1234","1234");
            securityService.saveNewUser("ali rochd","1234","1234");
            securityService.saveNewUser("yousra chafik","1234","1234");
            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");
            securityService.addRoletoUser("abdelhadi elkhattabi","USER");
            securityService.addRoletoUser("abdelhadi elkhattabi","ADMIN");
            securityService.addRoletoUser("yousra chafik","USER");
            securityService.addRoletoUser("ali rochd","USER");


        };

    }


}
