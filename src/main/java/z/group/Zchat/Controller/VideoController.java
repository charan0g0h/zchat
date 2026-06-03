package z.group.Zchat.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import z.group.Zchat.Entity.CallHistory;
import z.group.Zchat.Entity.VideoCall;
import z.group.Zchat.Repo.CallHistoryRepo;

import java.security.Principal;
import java.util.List;

@RestController
public class VideoController {
    SimpMessagingTemplate simpMessagingTemplate;
    SimpUserRegistry simpUserRegistry;
    CallHistoryRepo callHistoryRepo;
    public VideoController(SimpMessagingTemplate simpMessagingTemplate,CallHistoryRepo callHistoryRepo , SimpUserRegistry simpUserRegistry){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this. callHistoryRepo =  callHistoryRepo;
    }

    @MessageMapping("/video-call")
    public void startVideocall(VideoCall videoCall, Principal principal){
        videoCall.setFromUsername(principal.getName());
        if(videoCall.getMode().equals("IdSent")){
            CallHistory call = new CallHistory();
            call.setFromUsername(videoCall.getToUsername());
            call.setToUsername(videoCall.getFromUsername());
            call.setStatus("Answered");
            call.setDate(videoCall.getCallDate());
            callHistoryRepo.save(call);
        }else if(videoCall.getMode().equals("RequestReject")){
            CallHistory call = new CallHistory();
            call.setFromUsername(videoCall.getToUsername());
            call.setToUsername(videoCall.getFromUsername());
            call.setStatus("Rejected");
            call.setDate(videoCall.getCallDate());
            callHistoryRepo.save(call);
        }
        simpMessagingTemplate.convertAndSendToUser(videoCall.getToUsername(),"/queue/videocall",videoCall);
    }

    @GetMapping("/gethistory")
    public List<CallHistory> gethistory(Principal principal){
        return callHistoryRepo.getCallHistory(principal.getName());
    }
}

