package kz.attractorschool.microgram.dto;

import kz.attractorschool.microgram.model.Comment;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CommentDTO {
    public static CommentDTO from(Comment comment){
        return builder()
                .id(comment.getId())
                .commenter(comment.getCommenter())
                .dateTime(comment.getDateTime())
                .path(comment.getPath())
                .post(comment.getPost())
                .text(comment.getText())
                .build();
    }

    private String id;
    private User commenter;
    private LocalDateTime dateTime;
    private String path;
    private Post post;
    private String text;
}
