package z.group.Zchat.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Configurations.Status;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Records.FriendRequestStatus;
import z.group.Zchat.Repo.FriendsRepo;

import java.util.List;

@RestController
public class FriendController {
    FriendsRepo friendsRepo;
    Authentication authentication;
    public FriendController(FriendsRepo friendsRepo){
        this.friendsRepo = friendsRepo;
    }

    @PostMapping("/send_friendRequest/{friend_username}")
    public Status sent_request(
            @PathVariable String friend_username
    ){
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return new Status(0, "not authenticated, login to sent request");
            }
            String username = authentication.getName();
            if (username == null) {
                return new Status(1, "login to sent request");
            }
            if (friendsRepo.existsByUsernameAndFriendUsername(username, friend_username))
                return new Status(2, "request already sent");
            Friends friend = new Friends();
            friend.setUsername(username);
            friend.setFriend_username(friend_username);
            friend.setStatus(FriendRequestStatus.Pending);
            friendsRepo.save(friend);
            return new Status(4, "request sent");
        }catch (Exception e){
            return new Status(5,e.getLocalizedMessage());
        }
    }

    @PutMapping ("/accept_friendRequest/{friend_username}")
    public Status accept_request(
            @PathVariable String friend_username
    ){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return new Status(0, "not authenticated, login to sent request");
        }
        String username = authentication.getName();
        if(username == null) {
            return new Status(1,"login to sent request");
        }
        if(!friendsRepo.existsByUsernameAndFriendUsername(friend_username , username))
            return new Status(2,"no request to accept");
        Friends friend = friendsRepo.getByUsernameAndFriendUsername(friend_username,username);
        friend.setStatus(FriendRequestStatus.Accepted);
        friendsRepo.save(friend);
        return new Status(4,"accepted request");
    }

    @DeleteMapping("/reject_friendRequest/{friend_username}")
    public Status reject_request(
            @PathVariable String friend_username
    ){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return new Status(0, "not authenticated, login to sent request");
        }
        String username = authentication.getName();
        if(username == null) {
            return new Status(1,"login to sent request");
        }
        if(!friendsRepo.existsByUsernameAndFriendUsername(friend_username , username))
            return new Status(2,"no request to reject");
        friendsRepo.deleteByUsernameAndFriendUsername(friend_username , username);
        if(friendsRepo.existsByUsernameAndFriendUsername(friend_username , username)){
            return new Status(3,"try again");
        }else return new Status(4,"request rejected");
    }

    @GetMapping("getallfriends")
    public List<Friends> getallfriends(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        String username = authentication.getName();
        if(username == null) {
            return null;
        }
       return friendsRepo.findAllByUsernameAndStatus(username , FriendRequestStatus.Accepted);
    }

    @GetMapping("getallrequest")
    public List<Friends> getallrequest(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        String username = authentication.getName();
        if(username == null) {
            return null;
        }
        return friendsRepo.findAllByFriendUsernameAndStatus(username,FriendRequestStatus.Pending);
    }

    @GetMapping("getallrequested")
    public List<Friends> getallrequested(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        String username = authentication.getName();
        if(username == null) {
            return null;
        }
        return friendsRepo.findAllByUsernameAndStatus(username,FriendRequestStatus.Pending);
    }



    @GetMapping("/isFriend/{friendUsername}")
    public Status isFriend(
            @PathVariable String friendUsername
    ){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String username =  authentication.getName();new Status(0,"this is your account");
            if(username.equals(friendUsername)) return new Status(0, "");
            if(username != null && friendsRepo.existsByUsernameAndFriendUsername(username,friendUsername)){
                Friends friends = friendsRepo.getByUsernameAndFriendUsername(username,friendUsername);
                if(friends.getStatus() == FriendRequestStatus.Accepted) return new Status(0,"already a friend");
                else return new Status(1,"waiting to accept");
            }
            return  new Status(2,"not a friend");
        }
        return new Status(3,"not authenticated");
    }
}
