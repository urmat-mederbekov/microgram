package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Comment {

    @Id
    private String id;
    private String text;
    private LocalDateTime dateTime;

    public Comment(String id, String text, LocalDateTime dateTime) {
        this.id = id;
        this.text = text;
        this.dateTime = dateTime;
    }
}
