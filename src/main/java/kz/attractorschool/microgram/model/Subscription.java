package kz.attractorschool.microgram.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "subscriptions")
@Data
public class Subscription {
    @Id
    private String id;
    @DBRef
    private User following;
    @DBRef
    private User follower;
    private LocalDateTime dateTime;

    public Subscription(User follower, User following) {
        this.id = UUID.randomUUID().toString();
        this.following = following;
        this.follower = follower;
        this.dateTime = LocalDateTime.now();
    }
}
