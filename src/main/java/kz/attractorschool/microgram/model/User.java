package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {

    @Id
    private String id;
    private String accountName;
    private String email;
    private String password;
    private int numOfPosts;
    private int numOfFollowers;
    private int numOfFollowings;

    public User(String id, String accountName, String email, String password, int numOfPosts, int numOfFollowers, int numOfFollowings) {
        this.id = id;
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.numOfPosts = numOfPosts;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowings = numOfFollowings;
    }
}
