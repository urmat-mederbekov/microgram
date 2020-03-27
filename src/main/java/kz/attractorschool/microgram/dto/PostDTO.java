package kz.attractorschool.microgram.dto;

import kz.attractorschool.microgram.model.Post;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class PostDTO {
    public static PostDTO from(Post post){
        String postImageId = post.getImage() == null
                ? "-no-image-id"
                : post.getImage().getId();

        return builder()
                .id(post.getId())
                .image(postImageId)
                .description(post.getDescription())
                .numOfLikes(post.getNumOfLikes())
                .numOfComments(post.getNumOfComments())
                .dateTime(post.getDateTime())
                .build();
    }

    private String id;
    private String image;
    private String description;
    private int numOfLikes;
    private int numOfComments;
    private LocalDateTime dateTime;
}
