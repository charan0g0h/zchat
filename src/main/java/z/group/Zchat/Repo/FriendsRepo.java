package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Records.FriendRequestStatus;

import java.util.List;

@Repository
public interface FriendsRepo extends JpaRepository<Friends , Integer> {
    boolean existsByUsernameAndFriendUsername(String username, String friend_username);
    Friends getByUsernameAndFriendUsername(String username , String friend_username);
    void deleteByUsernameAndFriendUsername(String username , String friend_username);
    List<Friends> findAllByUsernameAndStatus(String username , FriendRequestStatus status) ;
    List<Friends> findAllByFriendUsernameAndStatus(String friend_username , FriendRequestStatus status);

    List<Friends> findAllByFriendUsername(String username);

    List<Friends> findAllByUsername(String username);
}
