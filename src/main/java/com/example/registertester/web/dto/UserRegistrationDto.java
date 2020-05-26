package com.example.registertester.web.dto;


import com.example.registertester.constraint.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.AssertTrue;

/*Här använder vi vår egna annotationer och kopplar dem till vår klass. List är den innre annotationen, som har en matris av
 FieldMatch.
* Här lägger vi 2 FieldMatch-annotationer. Vi kopplar variabeln first på annotationen med variabeln password i
* UseerRegistrationDto. Samma princip för de andra variablerna second och message.*/
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
/*Detta är ett DTO-objekt, som lagrar all information som överförs från klienten (webbläsaren) till
backendsystemet (denna webb-app), när användaren fyller i formuläret.
* Vi använder den här klassen för att validera registreringsformuläret. Denna DTO valideras med Hibernate Validator-annotioner.
*Vi använder också vår egen @FieldMatch-annotation,som validerar om lösenordet är lika med det
* bekräftade lösenordet och om e-postadressfältet är lika med det bekräftade e-postadressfältet.*/
public class UserRegistrationDto {
//kontrollerar att fälten inte är tomma
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
//kontrollerar att det är en korrekt formaterad mejl-address
    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;
//kontrollerar att terms är true, alltså om användaren har accepterat villkoren.
    @AssertTrue
    private Boolean terms;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

}
