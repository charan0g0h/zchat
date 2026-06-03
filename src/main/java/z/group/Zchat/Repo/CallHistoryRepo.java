package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.CallHistory;

import java.util.List;

@Repository
public interface CallHistoryRepo extends JpaRepository<CallHistory , Integer> {
    @Query("SELECT c FROM CallHistory c WHERE " +
            " c.toUsername = :username or c.fromUsername = :username ")
    List<CallHistory> getCallHistory(@Param("username") String username);
}
