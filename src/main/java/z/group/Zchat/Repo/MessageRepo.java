package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer> {
    List<Message> findAllByFromUsername(String fromUsername);

    @Query("""
        SELECT m 
        FROM Message m
        WHERE m.fromUsername = :username 
           OR m.toUsername = :username
        ORDER BY m.sentDate ASC
    """)
    List<Message> getAllMessagesOfUser(@Param("username") String username);
}
