package kz.attractorschool.microgram.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Posts")
@Data
public class Post {

    @Id
    private String id;
    private String image;
    private String description;
    private LocalDateTime dateTime;

    public Post(String id, String image, String description, LocalDateTime dateTime) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.dateTime = dateTime;
    }
}
