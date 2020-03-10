package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "likes")
@Data
//@CompoundIndex(def = "{'user': 1. 'post': 1}")
public class Like {

    @Id
    private String id;
    private String user;
    private String post;
    private LocalDateTime dateTime;
    private static List<Like> likes = makeLikes();

    public Like(String user, String post, LocalDateTime dateTime) {
        this.id =  UUID.randomUUID().toString();
        this.user = user;
        this.post = post;
        this.dateTime = dateTime;
    }
    private static List<Like> makeLikes(){
        LocalDateTime dateTime = LocalDateTime.now();
        List<Like> likes = new LinkedList<>();
        likes.add(new Like(User.getUsers().get(2).getAccountName(), Post.getPosts().get(1).getDescription(), dateTime.minusDays(4)));
        likes.add(new Like(User.getUsers().get(3).getAccountName(), Post.getPosts().get(2).getDescription(), dateTime.minusDays(2)));
        likes.add(new Like(User.getUsers().get(3).getAccountName(), Post.getPosts().get(1).getDescription(), dateTime.minusDays(3)));
        return  likes;
    }

    public static List<Like> getLikes(){
        return likes;
    }
}
