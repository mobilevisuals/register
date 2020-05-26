package com.example.registertester.service;


import com.example.registertester.model.Role;
import com.example.registertester.model.User;
import com.example.registertester.repository.UserRepository;
import com.example.registertester.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService {
/*I den h�r klassen implementerar metoderna f�r att leta upp en anv�ndare via e-post och spara anv�ndarregistreringen
med UserRegistrationDto.*/

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public com.example.registertester.model.User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
/*Det �r viktigt att spara anv�ndarens l�senord med BCryptPasswordEncoder, n�r anv�ndaren sparas.
Annars kan databasadministrat�ren se l�senordet i klar text.*/
    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        //Vi g�r en lista av roller, eftersom det kr�vs av User-klassen
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
/*Vi f�r h�r ett objekt av Springs User-klass, inte v�r User-klass.
Denna �r en modell av anv�ndarinformationen, som h�mtas av UserDetailsService.
Klassen implementerar UserDetails.
Konstruktorn till User beh�ver en Collection av Authority.
D�rf�r anropas metoden mapRolesToAuthorities.
*/
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map// l�ter oss omvandla ett objekt till n�got annat.
                //Role omvandlas till SimpleGrantedAuthority
                        (role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());//l�gger alla objekten i v�r stream i en Collection
        //collect() en avslutande metod i Stream API

    }
}
