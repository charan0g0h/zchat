package z.group.Zchat.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Records.SearchAcc;
import z.group.Zchat.Repo.AccountRepo;
import z.group.Zchat.Repo.FriendRepo;

import java.util.LinkedList;
import java.util.List;

@RestController
public class FriendController {
    public FriendRepo friendRepo;
    public AccountRepo accountRepo;
    public Authentication authentication;
    public FriendController(FriendRepo friendRepo , AccountRepo accountRepo ){
        this.friendRepo = friendRepo;
        this.accountRepo = accountRepo;
    }

    @GetMapping("/getallfollowers")
    public List<SearchAcc> getAllFollowers(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null && authentication.isAuthenticated()){
            username = authentication.getName();
        }
        Account account = accountRepo.getByUsername(username);
        return friendRepo.getallfollowersAcc(account.acc_id);
    }

    @GetMapping("/getallfollowing")
    public List<SearchAcc> getALLFollowing(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null && authentication.isAuthenticated()){
            username = authentication.getName();
        }
        Account account = accountRepo.getByUsername(username);
        return friendRepo.getallfollowingAcc(account.acc_id);
    }

}
