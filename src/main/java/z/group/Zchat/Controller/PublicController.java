package z.group.Zchat.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Entity.Zaps.Zap;
import z.group.Zchat.Records.FriendRequestStatus;
import z.group.Zchat.Records.Profile;
import z.group.Zchat.Records.PublicProfile;
import z.group.Zchat.Records.SearchAcc;
import z.group.Zchat.Repo.AccountRepo;
import z.group.Zchat.Repo.FriendsRepo;
import z.group.Zchat.Repo.ZapRepo;

import java.util.List;

@RestController
public class PublicController {
    public AccountRepo accountrepo;
    public Authentication authentication;
    public ZapRepo zapRepo;
    public FriendsRepo friendsRepo;
    public PublicController(AccountRepo accountRepo ,ZapRepo zapRepo , FriendsRepo friendsRepo){
        this.accountrepo = accountRepo;
        this.zapRepo = zapRepo;
        this.friendsRepo = friendsRepo;
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

    @GetMapping("/searchProfile/{username}")
    public PublicProfile searchProfile(
            @PathVariable String username
    ){
        Account account = accountrepo.getByUsername(username);
        List<Zap> zapList = zapRepo.findAllByUsername(username);
        int friendsCount = friendsRepo.findAllByUsernameAndStatus(username, FriendRequestStatus.Accepted).size();
        return new PublicProfile(
                username,
                account.getEmail(),
                account.getFullname(),
                account.getCreationdate(),
                zapList,
                friendsCount
        );
    }


}
