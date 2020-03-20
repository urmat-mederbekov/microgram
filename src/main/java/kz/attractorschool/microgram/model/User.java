package kz.attractorschool.microgram.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "users")
@Data
public class User {
    public static final User EMPTY = new User("No one", "none@gmail.com", "nothing");

    @Id
    private String id;
    @Indexed
    private String username;
    @Indexed
    private String email;
    private String password;
    private int numOfPosts = 0;
    private int numOfFollowers;
    private int numOfFollowings;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
