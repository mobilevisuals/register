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
Sen ”GetMapping och Postmapping till olika metoder, vilket gör att Post till /registration kommer starta
registerUserAccount och Get till samma URL kommer starta showRegistrationForm
*/
public class UserRegistrationController {

    private UserService userService;

    @Autowired
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

/* @ModelAttribute("user") på metodnivå:
Definierar ett objekt som ska vara en del av Model
Så userRegistrationDto injiceras här och kommer att vara tillgänglig med namnet "user" i Model
Vi använder userRegistrationDto för att validera och processa registrerings-formuläret.
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
    @ModelAttribute("user") på parameter-nivå:
  UserRegistrationDto kommer att läggas till som en property kallad "user" till Model-objektet i MVC.
  Informationen i det sända formuläret kopplas till ett objekt, som blir userDto.
  Vi använder userDto för att validera och processa informationen, som skickats med registrerings-formuläret.
Valideringen kommer att ske automatiskt och eventuella fel kommer att finnas i BindingResult.
  * */
    @PostMapping
    //@Valid : för att sätta igång validering av userDto
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){
//Vi kontrollerar om en användare redan finns.
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
//Om formuläret har några fel återgår vi till registreringssidan.
        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        //Annars gör vi redirect till registration-sidan och informerar användaren om att registreringsproceduren är klar.
        //Vi skickar success som en parameter till webbsidan
        return "redirect:/registration?success";
    }

}
