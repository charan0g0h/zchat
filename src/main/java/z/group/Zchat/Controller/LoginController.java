package z.group.Zchat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Configurations.JwtConfig;
import z.group.Zchat.Configurations.Status;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Records.Otprequest;
import z.group.Zchat.Records.Login;
import z.group.Zchat.Records.Otpverify;
import z.group.Zchat.Records.Register;
import z.group.Zchat.Repo.AccountRepo;
import z.group.Zchat.Service.EmailService;

import java.time.Duration;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class LoginController {
    AccountRepo accountRepo;
    AuthenticationManager manager;
    EmailService emailService;
    JwtConfig jwtConfig;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MailProperties mailProperties;
    public LoginController(AccountRepo accountRepo , AuthenticationManager manager , JwtConfig jwtConfig , EmailService emailService){
        this.accountRepo = accountRepo;
        this.emailService = emailService;
        this.manager = manager;
        this.jwtConfig = jwtConfig;
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @PostMapping("/register")
    public Status register(@RequestBody Register reg){
        Account exist = accountRepo.getByUsername(reg.username());
        if(reg.username() == null ){
            return new Status(3,"username empty ");
        }
        if(exist != null){
            return new Status(3," not available");
        }
        if(reg.password() == null || reg.password().length() < 8){
            return new Status(1,"password missing or password is small(min length 8)");
        }
        if (reg.email() == null || !reg.email().contains("@")) {
            return new Status(2,"check email");
        }

        Account account = new Account();
        account.setUsername(reg.username());
        account.setFullname(reg.fullname());
        account.setPassword(encoder.encode(reg.password()));
        account.setEmail(reg.email());
        account.setCreationdate(new Date());
        accountRepo.save(account);
        return new Status(0,"success");
    }


    @PostMapping("/login")
    public Status login(@RequestBody Login login){
        try{

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
            e.printStackTrace();
            return new Status(12,"incorrect username or password");
        }
    }

    @PostMapping("/send-otp")
    public Status sendOtp(
            @RequestBody Otprequest otprequest
    ){
        try {
            String otp = "123456";
            emailService.sendMail(otprequest.email(), "OTP", otp);
            stringRedisTemplate.opsForValue().set(
                    otprequest.email(),
                    otp,
                    Duration.ofSeconds(300)
            );
            return new Status(0,"otp sent succesfully");
        }catch (Exception e){
            return new Status(1,e.getLocalizedMessage());
        }
    }

    @PostMapping("/verify-otp")
    public Status verifyOtp(
            @RequestBody Otpverify otpverify
            ){
        try{
            String storedKey = stringRedisTemplate.opsForValue().get(otpverify.email());
            System.out.println(storedKey);
            if(storedKey == null){
                return new Status(1,"try again");
            }
            if(storedKey.equals(otpverify.otp())){
                return new Status(0,"otp verified");
            }else{
                return new Status(2,"incorrect otp");
            }
        }catch (Exception e){
            return new Status(3,e.getLocalizedMessage());
        }
    }
}
