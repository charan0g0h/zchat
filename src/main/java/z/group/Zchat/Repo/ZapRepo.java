package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import z.group.Zchat.Entity.Zaps.Zap;

import java.util.List;

@Repository
public interface ZapRepo extends JpaRepository<Zap , Integer> {
    List<Zap> findByUsernameInOrderByDateDesc(
            List<String> usernames
    );

    List<Zap> findAllByUsername(
            String username
    );
}
