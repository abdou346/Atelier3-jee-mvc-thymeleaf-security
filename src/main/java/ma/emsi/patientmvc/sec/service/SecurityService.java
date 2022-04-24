package ma.emsi.patientmvc.sec.service;

import ma.emsi.patientmvc.sec.entities.AppRole;
import ma.emsi.patientmvc.sec.entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String roleName,String description);
    void addRoletoUser(String username,String roleName);
    void removeRoleFromUser(String username,String roleName);
    AppUser loadUserByUserName(String Username);
}
