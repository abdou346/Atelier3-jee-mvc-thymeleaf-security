package ma.emsi.patientmvc.web;


import lombok.AllArgsConstructor;
import lombok.Value;
import ma.emsi.patientmvc.entities.Patient;
import ma.emsi.patientmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor

public class PatientController {
    private PatientRepository patientRepository ;

    @GetMapping(path="/user/index")
public  String patients(Model model,
                        @RequestParam(name="page" , defaultValue = "0") int page,
                        @RequestParam(name="size" , defaultValue = "5")int size,

                        @RequestParam(name="keyword",defaultValue ="")  String keyword)
{
    int score;
   Long l;
    Page<Patient> patients ;
    Optional<Patient> result ;
    if(!keyword.equals("") && isInteger(keyword) ) {
        score=Integer.valueOf(keyword) ;


        patients=patientRepository.findByScore(score,PageRequest.of(page , size));
    }
    else if(keyword.length()==1){
      patients=patientRepository.findBySexe(keyword,PageRequest.of(page , size));


    }
    else {
        patients=patientRepository.findByNomContainsOrCinContains(keyword,keyword,PageRequest.of(page , size));
    }

    model.addAttribute("ListPatients",patients.getContent())  ;
    model.addAttribute("pages" , new int[patients.getTotalPages()]) ;
    model.addAttribute("currentPage" , page) ;
    model.addAttribute("keyword" , keyword) ;
    model.addAttribute("totalPages",patients.getTotalPages());



    return "patients" ;
}

@GetMapping(path="/admin/delete")
public String delete(Long id,@RequestParam(name="keyword" , defaultValue = "")  String keyword ,
                     @RequestParam(name="page" , defaultValue = "0") int page)
{
    patientRepository.deleteById(id);
    return "redirect:/user/index?page="+page+"&keyword="+keyword ;
}

    @GetMapping(path="/user/patients")
    @ResponseBody
    public List<Patient> listPatients()
    {

        return patientRepository.findAll() ;
    }

    @GetMapping(path="/admin/formPatients")
    public String formPatients(Model model)
    {
        model.addAttribute("patient" , new Patient()) ;
        return "formPatients" ;
    }
    @PostMapping(path="/admin/save")
    public String save(Model model , @Valid Patient patient , BindingResult bindingResult  ,
        @RequestParam(defaultValue="0")int page,
    @RequestParam(defaultValue="")String keyword){
        if(bindingResult.hasErrors())
              return "formPatients" ;

       patientRepository.save(patient) ;
        return "redirect:/user/index?page="+page+"&keyword="+keyword ;
    }
    @GetMapping(path="/admin/editPatient")
    public String editPatient(Model model , Long id ,String keyword , int page )
    {
       Patient p =  patientRepository.findById(id).orElse(null) ;
       if (p==null)
       {
           throw  new RuntimeException("Patient introuvable ") ;
       }
       model.addAttribute("page",page) ;
       model.addAttribute("keyword" , keyword ) ;
        model.addAttribute("patient" ,p) ;
        return "editPatient" ;
    }

    @GetMapping(path="/")
    public String home()
    {
        return "home" ;
    }
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }


}