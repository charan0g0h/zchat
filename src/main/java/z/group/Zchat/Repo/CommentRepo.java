package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Zaps.Comments;

@Repository
public interface CommentRepo extends JpaRepository<Comments , Integer> {
}
