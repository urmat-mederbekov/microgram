package kz.attractorschool.microgram.dto;

import kz.attractorschool.microgram.model.Like;
import kz.attractorschool.microgram.model.Post;
import lombok.*;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class PostDTO {
    public static PostDTO from(Post post){
        return builder()
                .id(post.getId())
                .image(post.getImage())
                .description(post.getDescription())
                .numOfLikes(post.getNumOfLikes())
                .likes(post.getLikes())
                .build();
    }

    private String id;
    private String image;
    private String description;
    private int numOfLikes;
    private List<Like> likes;
}
