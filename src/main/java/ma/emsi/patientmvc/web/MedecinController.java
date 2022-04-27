package ma.emsi.patientmvc.web;


import lombok.AllArgsConstructor;
import ma.emsi.patientmvc.entities.Medecin;
import ma.emsi.patientmvc.entities.Patient;
import ma.emsi.patientmvc.repositories.MedecinRepository;
import ma.emsi.patientmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor

public class MedecinController {
    private MedecinRepository medecinRepository;
    @GetMapping(path="/user/index/Medecin")
public  String medecins(Model model,
                        @RequestParam(name="page" , defaultValue = "0") int page,
                        @RequestParam(name="size" , defaultValue = "5")int size,
                        @RequestParam(name="keyword",defaultValue ="")  String keyword)
    {
        Page<Medecin> medecins ;

      if(keyword.length()==1){
            medecins=medecinRepository.findBySexe(keyword,PageRequest.of(page , size));


        }
        else {
            medecins=medecinRepository.findByNomContainsOrSpecialiteContains(keyword,keyword,PageRequest.of(page , size));

        }

    model.addAttribute("ListMedecin",medecins.getContent())  ;
    model.addAttribute("pages" , new int[medecins.getTotalPages()]) ;
    model.addAttribute("currentPage" , page) ;
    model.addAttribute("keyword" , keyword) ;
        model.addAttribute("totalPages",medecins.getTotalPages());


    return "medecin" ;
}

@GetMapping(path="/admin/delete/Medecin")
public String delete(Long id,@RequestParam(name="keyword" , defaultValue = "")  String keyword ,
                     @RequestParam(name="page" , defaultValue = "0") int page)
{
    medecinRepository.deleteById(id);
    return "redirect:/user/index/Medecin?page="+page+"&keyword="+keyword ;
}

    @GetMapping(path="/user/Medecin")
    @ResponseBody
    public List<Medecin> listMedecin()
    {

        return medecinRepository.findAll() ;
    }

    @GetMapping(path="/admin/formMedecin")
    public String formPatients(Model model)
    {
        model.addAttribute("medecin" , new Medecin()) ;
        return "formMedecin" ;
    }
    @PostMapping(path="/admin/Medecin/save")
    public String save(Model model , @Valid Medecin medecin , BindingResult bindingResult  ,
        @RequestParam(defaultValue="0")int page,
    @RequestParam(defaultValue="")String keyword){
        if(bindingResult.hasErrors())
              return "formMedecin" ;

       medecinRepository.save(medecin) ;
        return "redirect:/user/index/Medecin?page="+page+"&keyword="+keyword ;
    }
    @GetMapping(path="/admin/editMedecin")
    public String editMedecin(Model model , Long id ,String keyword , int page )
    {
       Medecin m =  medecinRepository.findById(id).orElse(null) ;
       if (m==null)
       {
           throw  new RuntimeException("Patient introuvable ") ;
       }
       model.addAttribute("page",page) ;
       model.addAttribute("keyword" , keyword ) ;
        model.addAttribute("medecin" ,m) ;
        return "editMedecin" ;
    }


}
