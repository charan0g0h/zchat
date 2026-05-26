package z.group.Zchat.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import z.group.Zchat.Configurations.JwtConfig;
import z.group.Zchat.Configurations.Status;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Records.Login;
import z.group.Zchat.Records.Register;
import z.group.Zchat.Repo.AccountRepo;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class LoginController {
    AccountRepo accountRepo;
    AuthenticationManager manager;
    JwtConfig jwtConfig;
    public LoginController(AccountRepo accountRepo , AuthenticationManager manager , JwtConfig jwtConfig){
        this.accountRepo = accountRepo;
        this.manager = manager;
        this.jwtConfig = jwtConfig;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @PostMapping("/register")
    public Status register(@RequestBody Register reg){
        if(reg.password() == null || reg.password().length() < 8){
            return new Status(1,"password missing");
        }
        if (reg.email() == null || !reg.email().contains("@")) {
            return new Status(2,"check email");
        }

        Account account = new Account();
        account.setUsername(reg.username());
        account.setPassword(encoder.encode(reg.password()));
        account.setEmail(reg.email());
        account.setCreationdate(new Date());
        accountRepo.save(account);
        return new Status(0,"success");
    }


    @PostMapping("/login")
    public Status login(@RequestBody Login login){
        try{
            if(!accountRepo.existsByUsername(login.username())) return new Status(4,"username not found");
            if(login.password() == null) return new Status(1,"password missing");
            if(login.username() == null) return new Status(2,"username missing");
            Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.username(),
                    login.password(),
                    null
            ));
            if(authentication.isAuthenticated()){
                return new Status(0, jwtConfig.getJwt(login.username()));
            }
            return new Status(3,"login failed");
        }catch(Exception e){
            return new Status(12,"incorrect username or password");
        }
    }
}
