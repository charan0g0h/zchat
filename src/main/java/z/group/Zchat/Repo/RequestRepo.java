package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Request;

import java.util.List;

@Repository
public interface RequestRepo extends JpaRepository<Request,Integer> {
    @Query("SELECT r FROM Request r WHERE r.fromId = :fromId and r.toId = :toId" )
    List<Request> checkRequest(@Param("fromId") Integer fromId,@Param("toId") Integer toId);
}
