package z.group.Zchat.Records;

import java.util.Date;
import java.util.List;

public record Profile(
        String username ,
        String fullname ,
        String email,
        Date creationDate
) {
}
