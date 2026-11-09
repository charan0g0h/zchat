package z.group.Zchat.Controller;

import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import z.group.Zchat.Configurations.Status;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Entity.Zaps.Comments;
import z.group.Zchat.Entity.Zaps.React;
import z.group.Zchat.Entity.Zaps.ReactType;
import z.group.Zchat.Entity.Zaps.Zap;
import z.group.Zchat.Records.ClientComment;
import z.group.Zchat.Records.CommentResponse;
import z.group.Zchat.Records.FriendRequestStatus;
import z.group.Zchat.Records.ReadZap;
import z.group.Zchat.Repo.CommentRepo;
import z.group.Zchat.Repo.FriendsRepo;
import z.group.Zchat.Repo.ReactRepo;
import z.group.Zchat.Repo.ZapRepo;

import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class ZapController {
    ZapRepo zapRepo;
    CommentRepo commentRepo;
    SimpMessagingTemplate simpMessagingTemplate;
    ReactRepo reactRepo;
    Authentication authentication;
    FriendsRepo friendsRepo;
    public ZapController(ZapRepo zapRepo, FriendsRepo friendsRepo,CommentRepo commentRepo,ReactRepo reactRepo , SimpMessagingTemplate simpMessagingTemplate){
        this.zapRepo = zapRepo;
        this.friendsRepo = friendsRepo;
        this.commentRepo = commentRepo;
        this.reactRepo = reactRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/createZap")
    public void createZap(
           ReadZap readZap,
           Principal principal
            )
    {
        Zap zap = new Zap();
        zap.setContent(readZap.content());
        zap.setHead(readZap.head());
        zap.setDate(new Date(System.currentTimeMillis()));
        zap.setUsername(principal.getName());
        zapRepo.save(zap);
        simpMessagingTemplate.convertAndSend("/topic/zaps",zap);
    }

    @MessageMapping("/createComment")
    @Transactional
    public void createComment(
            ClientComment clientComment,
            Principal principal
    ) {
        Zap zap = zapRepo.findById(clientComment.zapId())
                .orElseThrow(() -> new RuntimeException("Zap not found"));

        Comments comment = new Comments();
        comment.setCommenter(principal.getName());
        comment.setComment(clientComment.content());
        comment.setDate(new Date(System.currentTimeMillis()));
        comment.setZap(zap);
        commentRepo.save(comment);
        CommentResponse response = new CommentResponse(comment.getId(), clientComment.zapId(), principal.getName(),
                comment.getComment(), comment.getDate());
        simpMessagingTemplate.convertAndSend("/topic/comments",response);
    }

    @PostMapping("/likeZap/{zapId}")
    @Transactional
    public Map<String, Integer> likes(@PathVariable int zapId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Zap zap = zapRepo.findById(zapId)
                .orElseThrow(() -> new RuntimeException("Zap not found"));

        React react = reactRepo.findByUsernameAndZapId(username, zapId);

        if (react == null) {

            React newReact = new React();
            newReact.setUsername(username);
            newReact.setZapId(zapId);
            newReact.setReactType(ReactType.LIKE);

            reactRepo.save(newReact);

            zap.setLikes(zap.getLikes() + 1);

        } else if (react.getReactType() == ReactType.DISLIKE) {

            zap.setDislikes(zap.getDislikes() - 1);
            zap.setLikes(zap.getLikes() + 1);

            react.setReactType(ReactType.LIKE);
            reactRepo.save(react);

        } else {

            zap.setLikes(zap.getLikes() - 1);

            reactRepo.delete(react);
        }

        zapRepo.save(zap);

        return Map.of(
                "likes", zap.getLikes(),
                "dislikes", zap.getDislikes()
        );
    }
    @PostMapping("/dislikeZap/{zapId}")
    @Transactional
    public Map<String, Integer> dislikes(@PathVariable int zapId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Zap zap = zapRepo.findById(zapId)
                .orElseThrow(() -> new RuntimeException("Zap not found"));

        React react = reactRepo.findByUsernameAndZapId(username, zapId);

        if (react == null) {

            React newReact = new React();
            newReact.setUsername(username);
            newReact.setZapId(zapId);
            newReact.setReactType(ReactType.DISLIKE);

            reactRepo.save(newReact);

            zap.setDislikes(zap.getDislikes() + 1);

        } else if (react.getReactType() == ReactType.LIKE) {

            zap.setLikes(zap.getLikes() - 1);
            zap.setDislikes(zap.getDislikes() + 1);

            react.setReactType(ReactType.DISLIKE);
            reactRepo.save(react);

        } else {

            zap.setDislikes(zap.getDislikes() - 1);

            reactRepo.delete(react);
        }

        zapRepo.save(zap);

        return Map.of(
                "likes", zap.getLikes(),
                "dislikes", zap.getDislikes()
        );
    }

    @GetMapping("/getAllZaps")
    public List<Zap> getAllZaps(){
        return zapRepo.findAll();
    }

    @GetMapping("/getFriendsZaps")
    public List<Zap> getFriendsZaps(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if(authentication != null){
            username = authentication.getName();
        }
        if(username != null){
            List<Friends> friendsList = friendsRepo.findAllByUsernameAndStatus(username, FriendRequestStatus.Accepted);
            List<String> usernames = new LinkedList<>();
            for(Friends friend : friendsList){
                usernames.add(friend.getUsername());
            }
            return zapRepo.findByUsernameInOrderByDateDesc(usernames);
        }
        return null;
    }
}

