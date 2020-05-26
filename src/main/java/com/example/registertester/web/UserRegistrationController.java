package com.example.registertester.web;


import com.example.registertester.model.User;
import com.example.registertester.service.UserService;
import com.example.registertester.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
/*
Basmapping till /registration
Sen �GetMapping och Postmapping till olika metoder, vilket g�r att Post till /registration kommer starta
registerUserAccount och Get till samma URL kommer starta showRegistrationForm
*/
public class UserRegistrationController {

    private UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

/* @ModelAttribute("user") p� metodniv�:
Definierar ett objekt som ska vara en del av Model
S� userRegistrationDto injiceras h�r och kommer att vara tillg�nglig med namnet "user" i Model
Vi anv�nder userRegistrationDto f�r att validera och processa registrerings-formul�ret.
 * */
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto()
    {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {

        return "registration";
    }

    /*
    @ModelAttribute("user") p� parameter-niv�:
  UserRegistrationDto kommer att l�ggas till som en property kallad "user" till Model-objektet i MVC.
  Informationen i det s�nda formul�ret kopplas till ett objekt, som blir userDto.
  Vi anv�nder userDto f�r att validera och processa informationen, som skickats med registrerings-formul�ret.
Valideringen kommer att ske automatiskt och eventuella fel kommer att finnas i BindingResult.
  * */
    @PostMapping
    //@Valid : f�r att s�tta ig�ng validering av userDto
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){
//Vi kontrollerar om en anv�ndare redan finns.
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
//Om formul�ret har n�gra fel �terg�r vi till registreringssidan.
        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        //Annars g�r vi redirect till registration-sidan och informerar anv�ndaren om att registreringsproceduren �r klar.
        //Vi skickar success som en parameter till webbsidan
        return "redirect:/registration?success";
    }

}
