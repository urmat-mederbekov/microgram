package kz.attractorschool.microgram.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection="post_images")
public class PostImage {
    public static final PostImage NO_IMAGE = PostImage.builder().id("-NO-IMAGE-").build();
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    // здесь будем сохранять картинку для фильма
    @Builder.Default
    private Binary postData = new Binary(new byte[0]);
}
