package kz.attractorschool.microgram.dto;

import kz.attractorschool.microgram.model.Like;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LikeDTO {
    public static LikeDTO from(Like like){
        return builder()
                .id(like.getId())
                .liker(like.getLiker())
                .post(like.getPost())
                .dateTime(like.getDateTime())
                .build();
    }

    private String id;
    private User liker;
    private Post post;
    private LocalDateTime dateTime;
}
