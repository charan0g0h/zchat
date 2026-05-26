package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account,Integer> {
     Account getByUsername(String Username);
     boolean existsByUsername(String Username);
}
