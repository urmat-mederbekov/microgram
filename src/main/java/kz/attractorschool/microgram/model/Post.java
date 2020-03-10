package kz.attractorschool.microgram.model;

import kz.attractorschool.microgram.utils.Generator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "posts")
@Data
public class Post {
    static Generator gen = new Generator();

    @Id
    private String id;
    private String image;
//    @Indexed
    private String description;
    private LocalDateTime dateTime = LocalDateTime.now();
    private static List<Post> posts = makePosts();
    @DBRef
    private List<Like> likes = new LinkedList<>();

    public Post(String image, String description, LocalDateTime dateTime) {
        this.id =  UUID.randomUUID().toString();
        this.image = image;
        this.description = description;
        this.dateTime = dateTime;
    }
    private static List<Post> makePosts(){
        List<Post> posts = new LinkedList<>();
        LocalDateTime dateTime = LocalDateTime.now();
        posts.add(new Post("", Generator.makeDescription(), dateTime));
        posts.add(new Post("", Generator.makeDescription(), dateTime.minusDays(4)));
        posts.add(new Post("", Generator.makeDescription(), dateTime.minusMinutes(5)));
        return posts;
    }
    public static List<Post> getPosts(){
        return posts;
    }
    public void addLike(Like like){
        getLikes().add(like);
        setLikes(getLikes());
    }
}