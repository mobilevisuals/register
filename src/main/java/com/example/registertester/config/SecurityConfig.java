package com.example.registertester.config;


import com.example.registertester.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*Klassen ärver från samma WebSecurityConfigurerAdapter-klass som i förra projektet
	WebSecurityConfigurerAdapter är en klass för att ställa in säkerheten.
	Vår klass ärver från denna klass och gör override på flera av dess metoder
	@Configuration används för klasser med inställningar, som har en @bean-metod.
Denna metod returnerar en bean, vilken används av Spring.
*/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {

        this.userService = userService;
    }
/*Configure-metoden definierar vilka URL som ska vara säkra och vilka som ska vara öppna.
Med HttpSecurity-klassen går det att ställa in säkerheten för olika URL
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Alla statiska resurser och /registration ställs först in som öppna utan säkerhet.
        Exakt samma följd av metodanrop som i förra projektet fram till logout().
        * */
        http
                .authorizeRequests()
                .antMatchers(
                        "/registration**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
        /*Alla requests till /login kommer autentiseras genom ett formulär
De som blir blivit autentiserade och auktoriserade har rätt att komma åt login och logout-sidan
        När logout anropas får vi en LogoutConfigurer.
        De följande metod-anropen från invalidateHttpSession till permitAll
är ifrån detta LogoutConfigurer-objekt.
clearAuthentication() anger om vi ska ta bort autentiseringen vid utloggningstillfället.
invalidateHttpSession() ställer in SecurityContextLogoutHandler att ogiltigförklara HttpSessionen vid utloggning.
RequestMatcher utlöser utloggningen.
logoutSuccessUrl ställer in vilket URL, som det gå till efter utloggning.*/
    }
//BCryptPasswordEncoder är en implementation av PasswordEncoder, som använder BCrypt hashing funktionen.
    //metoden anropas av authenticationProvider()
    @Bean
    public BCryptPasswordEncoder
    passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
DaoAuthenticationProvider är en implementation av AuthenticationProvider-interface,
AuthenticationProvider är ett interface för klasser, som för olika autentiserings-implementationer.
Vi ska ju autentisera användaren och hämtar all information om
användaren från UserDetailsService.
Metoden anropas av configure-metoden nedanför.*/
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        //fungerar eftersom vår service implementerar UserDetailsService:
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
