package z.group.Zchat.Records;

import java.util.Date;

public record CommentResponse(
        Integer id,
        Integer zapId,
        String commenter,
        String comment,
        Date date
) {
}
