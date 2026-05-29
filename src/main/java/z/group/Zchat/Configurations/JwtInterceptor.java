package z.group.Zchat.Configurations;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtInterceptor implements ChannelInterceptor {
    private final JwtConfig jwtConfig;

    public JwtInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    @Override
    public Message<?> preSend(Message<?> message,
                              MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor( message, StompHeaderAccessor.class);

        assert accessor != null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader =
                    accessor.getFirstNativeHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("No JWT Token");
            }

            String token = authHeader.substring(7);

            if(!jwtConfig.checkexpiry(token)) {
                throw new IllegalArgumentException("Invalid Token");
            }

            String username = jwtConfig.getusername(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of()
                    );

            accessor.setUser(authentication);
        }

        return message;
    }
}
