package z.group.Zchat.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import z.group.Zchat.Entity.Account;
import z.group.Zchat.Repo.AccountRepo;

@Service
public class UserServiceDetails  implements UserDetailsService {
    public AccountRepo repo;
    public UserServiceDetails(AccountRepo accountRepo){
        this.repo = accountRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repo.getByUsername(username);
        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles("users")
                .build();
    }
}
