package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "subscriptions")
@Data
public class Subscription {
    @Id
    private String id;
    @Indexed
    private User following;
    @Indexed
    private User follower;
    private LocalDateTime dateTime;
    private static List<Subscription> subscriptions = makeSubscription();

    public Subscription(User follower, User following, LocalDateTime dateTime) {
        this.id = UUID.randomUUID().toString();
        this.following = following;
        this.follower = follower;
        this.dateTime = dateTime;
    }
    private static List<Subscription> makeSubscription(){
        List<Subscription> subscriptions = new LinkedList<>();
        subscriptions.add(new Subscription(User.getUsers().get(0), User.getUsers().get(2), LocalDateTime.now().minusDays(3)));
        subscriptions.add(new Subscription(User.getUsers().get(1), User.getUsers().get(3), LocalDateTime.now().minusDays(9)));
        subscriptions.add(new Subscription(User.getUsers().get(0), User.getUsers().get(3), LocalDateTime.now().minusYears(1)));

        return subscriptions;
    }
    public static List<Subscription> getSubscriptions(){
        return subscriptions;
    }
}
