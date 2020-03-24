package kz.attractorschool.microgram.dto;

import kz.attractorschool.microgram.model.User;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserDTO {
    public static UserDTO from(User user) {
        return builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .numOfPosts(user.getNumOfPosts())
                .numOfFollowers(user.getNumOfFollowers())
                .numOfFollowings(user.getNumOfFollowings())
                .build();
    }

    private String username;
    private String email;
    private String password;
    private int numOfPosts;
    private int numOfFollowers;
    private int numOfFollowings;
}
