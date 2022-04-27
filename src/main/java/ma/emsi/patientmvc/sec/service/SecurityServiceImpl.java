package ma.emsi.patientmvc.sec.service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.emsi.patientmvc.sec.entities.AppRole;
import ma.emsi.patientmvc.sec.entities.AppUser;
import ma.emsi.patientmvc.sec.repositories.AppRoleRepository;
import ma.emsi.patientmvc.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
       if(!password.equals(rePassword)) throw new RuntimeException("Passwords do not match ");
       String hashedPWD=passwordEncoder.encode(password);
       AppUser appUser=new AppUser();
       appUser.setUserid(UUID.randomUUID().toString());
       appUser.setUsername(username);
       appUser.setPassword(hashedPWD);
       appUser.setActive(true);
      AppUser savedAppUser= appUserRepository.save(appUser);
       return appUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {

        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole!=null)throw new RuntimeException("Role"+roleName+"Already exist");
        appRole =new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole=appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoletoUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser==null)throw new RuntimeException("User not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null)throw new RuntimeException("Role not found");
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser==null)throw new RuntimeException("User not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null)throw new RuntimeException("Role not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
