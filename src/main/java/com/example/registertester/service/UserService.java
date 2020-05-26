package com.example.registertester.service;


import com.example.registertester.model.User;
import com.example.registertester.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
/*Vi låter vårt interface ärva från UserDetailsService. Detta är en klass, som kan lagra information om användare.
Jämför med måndagens projektet, där vi bara använde UserDetailsService för att hårdkoda namn och lösenord.
*/
public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
