package z.group.Zchat.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Entity.Message;
import z.group.Zchat.Records.SearchAcc;
import z.group.Zchat.Repo.AccountRepo;
import z.group.Zchat.Repo.MessageRepo;

import java.security.Principal;
import java.util.*;

@RestController
public class MessageController {
    private SimpMessagingTemplate messagingTemplate;
    MessageRepo messageRepo;
    AccountRepo accountRepo;
    Authentication authentication;
    public MessageController(SimpMessagingTemplate messagingTemplate ,AccountRepo accountRepo, MessageRepo messageRepo){
        this.messagingTemplate = messagingTemplate;
        this.messageRepo = messageRepo;
        this.accountRepo = accountRepo;
    }

    @MessageMapping("/private-message")
    public void privateMessage(Message message , Principal principal) {
        message.setFromUsername(principal.getName());
        messageRepo.save(message);
        messagingTemplate.convertAndSendToUser(message.getToUsername(), "/queue/messages", message);
    }

    @GetMapping("/getallchats")
    public Map<String, List<Message>> getChats(){
        String myUsername = null;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            myUsername = authentication.getName();
        }
        Map<String, List<Message>> chats = new HashMap<>();
        if(myUsername != null){
            List<Message> messages = messageRepo.getAllMessagesOfUser(myUsername);
            for(Message msg : messages){
                String otherUser;
                if(msg.getFromUsername().equals(myUsername)){
                    otherUser = msg.getToUsername();
                }
                else{
                    otherUser = msg.getFromUsername();
                }
                chats.putIfAbsent(otherUser, new ArrayList<>());
                chats.get(otherUser).add(msg);
            }
        }
        return chats;
    }
    @PostMapping ("getallprofiles")
    public List<SearchAcc> getallProfiles(@RequestBody List<String> usernames){
        List<SearchAcc> accounts = new LinkedList<>();
        for(String user : usernames){
            Account account = accountRepo.getByUsername(user);
            accounts.add(new SearchAcc(account.getUsername(),account.getFullname(),account.getEmail()));
        }
        return accounts;
    }

}
