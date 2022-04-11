package ma.emsi.patientmvc;


import ma.emsi.patientmvc.entities.Patient;
import ma.emsi.patientmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository)
    {
        return  args -> {
            patientRepository.save(new Patient(null , "abdou " , new Date() , false , 185)) ;
            patientRepository.save(new Patient(null , "Hamza " , new Date() , true , 148)) ;
            patientRepository.save(new Patient(null , "farouk " , new Date() , false , 966)) ;
            patientRepository.save(new Patient(null , "Yassine " , new Date() , true , 164)) ;
            patientRepository.findAll().forEach(p->
            {
                System.out.println(p.getNom());
            });
        } ;
    }

}
