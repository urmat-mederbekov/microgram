package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class SubscriptionToUser {

    @Id
    private String id;
    private String following;
    private String followed;
    private LocalDateTime dateTime;

    public SubscriptionToUser(String id, String following, String followed, LocalDateTime dateTime) {
        this.id = id;
        this.following = following;
        this.followed = followed;
        this.dateTime = dateTime;
    }
}
