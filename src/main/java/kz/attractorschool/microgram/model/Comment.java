package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "comments")
@Data
//@CompoundIndex(def = "{'path': 1. 'userName': 1}")
public class Comment {

    @Id
    private String id;
    private String path = null;
    private String text;
    private LocalDateTime dateTime;
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
