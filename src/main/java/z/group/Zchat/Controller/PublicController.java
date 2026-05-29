package z.group.Zchat.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Configurations.Status;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Entity.Request;
import z.group.Zchat.Records.SearchAcc;
import z.group.Zchat.Repo.AccountRepo;
import z.group.Zchat.Repo.FriendRepo;
import z.group.Zchat.Repo.RequestRepo;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    public AccountRepo accountrepo;
    public Authentication authentication;
    public RequestRepo requestRepo;
    public FriendRepo friendRepo;
    public PublicController(AccountRepo accountRepo ,FriendRepo friendRepo, RequestRepo requestRepo){
        this.accountrepo = accountRepo;
        this.friendRepo = friendRepo;
        this.requestRepo = requestRepo;
    }

    @GetMapping("/search/{username}")
    public SearchAcc search(@PathVariable String username){
        Account account = accountrepo.getByUsername(username);
        return new SearchAcc(account.getUsername(),account.getFullname(),account.getEmail());
    }

    @PostMapping("/sendrequest")
    public Status sendRequest(@RequestBody String username){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = null;
        if(authentication != null || authentication.isAuthenticated()){
            myUsername = authentication.getName();
        }
        int from_id =  accountrepo.getByUsername(myUsername).getAcc_id();
        int to_id = accountrepo.getByUsername(username).getAcc_id();
        List<Request> requestList = requestRepo.checkRequest(from_id,to_id);
        if(!requestList.isEmpty()){
            return new Status(5,"already request sent");
        }else {
            requestRepo.save(new Request(from_id,to_id));
            return new Status(0,"success");
        }
    }

    @DeleteMapping("/acceptrequest")
    @Transactional
    public void acceptRequest(@RequestBody String username){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = null;
        if(authentication != null && authentication.isAuthenticated()){
            myUsername = authentication.getName();
        }
        int from_id =  accountrepo.getByUsername(myUsername).getAcc_id();
        int to_id = accountrepo.getByUsername(username).getAcc_id();
        Request request = requestRepo.checkRequest(from_id,to_id).getFirst();
        int id = request.getId();
        requestRepo.deleteById(id);
        friendRepo.save(new Friends(from_id,to_id));
    }

    @DeleteMapping("/rejectrequest")
    @Transactional
    public void rejectRequest(@RequestBody String username){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = null;
        if(authentication != null && authentication.isAuthenticated()){
            myUsername = authentication.getName();
        }
        int from_id =  accountrepo.getByUsername(myUsername).getAcc_id();
        int to_id = accountrepo.getByUsername(username).getAcc_id();
        Request request = requestRepo.checkRequest(from_id,to_id).getFirst();
        int id = request.getId();
        requestRepo.deleteById(id);
    }

}
