package z.group.Zchat.Configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import z.group.Zchat.Service.UserServiceDetails;

import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {
    public JwtConfig jwtConfig;
    public UserServiceDetails userServiceDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authheader;
        authheader = request.getHeader("Authorization");
        String token= "";
        String username = "" ;
        if(authheader != null && authheader.startsWith("Bearer ")){
            token = authheader.substring(7);
            username = jwtConfig.getusername(token);
        }
        if(!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = userServiceDetails.loadUserByUsername(username);
            if(jwtConfig.checkexpiry(token)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
