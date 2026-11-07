package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Zaps.React;

@Repository
public interface ReactRepo extends JpaRepository<React , Integer> {
    boolean existsByUsernameAndZapId(String username , int ZapId);
    React findByUsernameAndZapId(String username , int ZapId);
}
