package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "comments")
@Data
public class Comment {

    @Id
    private String id;
    @Indexed
    private String path = null;
    private String text;
    private LocalDateTime dateTime;
    @Indexed
    private String userName;

    public Comment(String text, LocalDateTime dateTime, String userName) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.dateTime = dateTime;
        this.userName = userName;
    }

    public Comment(String path, String text, LocalDateTime dateTime, String userName) {
        new Comment(text, dateTime, userName);
        this.path = path;
    }
}
