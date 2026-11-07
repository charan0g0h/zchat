package z.group.Zchat.Records;

import z.group.Zchat.Entity.Zaps.Zap;

import java.util.Date;
import java.util.List;

public record PublicProfile(
        String username,
        String email,
        String fullname,
        Date date,
        List<Zap> zapList,
        int friendsCount
) {
}
