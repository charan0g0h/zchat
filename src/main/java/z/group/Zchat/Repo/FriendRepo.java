package z.group.Zchat.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import z.group.Zchat.Entity.Friends;
import z.group.Zchat.Records.SearchAcc;

import java.util.List;

@Repository
public interface FriendRepo extends JpaRepository<Friends, Integer>{

    @Query("""
            SELECT a.username, a.fullname, a.email
            FROM Friends f
            JOIN Account a
            ON f.followerId = a.acc_id
            WHERE f.followingId = :id""")
    List<SearchAcc> getallfollowersAcc(@Param("id") int myId);

    @Query("""
            SELECT a.username, a.fullname, a.email
            FROM Friends f
            JOIN Account a
            ON f.followingId = a.acc_id
            WHERE f.followerId = :id""")
    List<SearchAcc> getallfollowingAcc(@Param("id") int myId);
}
