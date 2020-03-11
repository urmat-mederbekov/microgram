package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "users")
@Data
//@CompoundIndex(def = "{'accountName': 1. 'email': 1}")
public class User {
    public static final User EMPTY = new User("No one", "none@gmail.com", "nothing",0,0);

    @Id
    private String id;
    private String accountName;
    private String email;
    private String password;
    private int numOfPosts = 0;
    private int numOfFollowers;
    private int numOfFollowings;
    private static List<User> users = makeUsers();

    @DBRef
    private List<Post> posts = new LinkedList<>();

    public User(String accountName, String email, String password, int numOfFollowers, int numOfFollowings) {
        this.id = UUID.randomUUID().toString();
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.numOfPosts = getPosts().size();
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowings = numOfFollowings;
    }

    private static List<User> makeUsers() {
        List<User> users = new LinkedList<>();
        users.add(new User("thanos", "thanos@gmail.com", "thanos", 6, 324));
        users.add(new User("loki", "loki@gmail.com", "loki", 57, 444));
        users.add(new User("thor", "thor@gmail.com", "thor", 565, 546));
        users.add(new User("batman", "batman@gmail.com", "batman", 3567, 656));
        users.add(new User("superman", "superman@gmail.com", "superman", 9085, 633));
        return users;
    }

    public static List<User> getUsers() {
        return users;
    }

    public void addPost(Post newPost) {
        getPosts().add(newPost);
        setPosts(getPosts());
    }
    public void updateNumOfPosts(){
        this.numOfPosts = getPosts().size();
    }
}
