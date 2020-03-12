package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "subscriptions")
@Data
//@CompoundIndex(def = "{'following': 1. 'followed': 1}")
public class Subscription {
    @Id
    private String id;
    @Indexed
    private String following;
    @Indexed
    private String followed;
    private LocalDateTime dateTime;

    public Subscription(String following, String followed, LocalDateTime dateTime) {
        this.id = UUID.randomUUID().toString();
        this.following = following;
        this.followed = followed;
        this.dateTime = dateTime;
    }

}
