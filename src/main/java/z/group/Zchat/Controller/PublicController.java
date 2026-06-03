package z.group.Zchat.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Records.Profile;
import z.group.Zchat.Records.SearchAcc;
import z.group.Zchat.Repo.AccountRepo;

@RestController
public class PublicController {
    public AccountRepo accountrepo;
    public Authentication authentication;
    public PublicController(AccountRepo accountRepo ){
        this.accountrepo = accountRepo;
    }

    @GetMapping("/profile")
    public Profile getProfile(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null && authentication.isAuthenticated()){
            username = authentication.getName();
        }
        if(username != null) {
            Account account = accountrepo.getByUsername(username);
            return new Profile(account.getUsername(),account.getFullname(),account.getEmail(),account.getCreationdate());
        }else return null;
    }

    @GetMapping("/search/{username}")
    public SearchAcc search(@PathVariable String username){
        System.out.println(username + " username");
        Account account = accountrepo.getByUsername(username);
        System.out.println(account.getUsername());
        return new SearchAcc(account.getUsername(),account.getFullname(),account.getEmail());
    }



}
