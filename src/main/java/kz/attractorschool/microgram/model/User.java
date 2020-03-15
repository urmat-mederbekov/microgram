package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "users")
@Data
//@CompoundIndex(def = "{'accountName': 1. 'email': 1}")
public class User {
    public static final User EMPTY = new User("No one", "none@gmail.com", "nothing");

    @Id
    private String id;
    @Indexed
    private String accountName;
    @Indexed
    private String email;
    private String password;
    private int numOfPosts = 0;
    private int numOfFollowers;
    private int numOfFollowings;
    private static List<User> users = makeUsers();
    private boolean loggedIn = false;


    @DBRef
    private List<User> followings = new LinkedList<>();

    @DBRef
    private List<User> followers = new LinkedList<>();



    @DBRef
    private List<Post> posts = new LinkedList<>();

    public User(String accountName, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.numOfPosts = getPosts().size();
        this.numOfFollowers = getFollowers().size();
        this.numOfFollowings = getFollowings().size();
    }

    private static List<User> makeUsers() {
        List<User> users = new LinkedList<>();
        users.add(new User("thanos", "thanos@gmail.com", "thanos"));
        users.add(new User("loki", "loki@gmail.com", "loki"));
        users.add(new User("thor", "thor@gmail.com", "thor"));
        users.add(new User("batman", "batman@gmail.com", "batman"));
        users.add(new User("superman", "superman@gmail.com", "superman"));
        return users;
    }

    public static List<User> getUsers() {
        return users;
    }
    public static  void subscribe(int follower, int following){
        User.getUsers().get(following).addFollowers(User.getUsers().get(follower));
//        User.getUsers().get(follower).addFollowings(User.getUsers().get(following));
    }

    public void addPost(Post newPost) {
        getPosts().add(newPost);
        setPosts(getPosts());
        updateNumOfPosts();
    }
    public void addFollowers(User follower) {
        getFollowers().add(follower);
        setFollowers(getFollowers());
        updateNumOfFollowers();
    }
    public void addFollowings(User following) {
        getFollowings().add(following);
        setFollowings(getFollowings());
        updateNumOfFollowings();
    }

    public void updateNumOfPosts(){
        this.numOfPosts = getPosts().size();
    }
    public void updateNumOfFollowers(){
        this.numOfFollowers = getFollowers().size();
    }
    public void updateNumOfFollowings(){
        this.numOfFollowings = getFollowings().size();
    }
}
