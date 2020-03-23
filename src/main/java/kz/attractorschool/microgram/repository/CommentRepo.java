package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<Comment, String> {
    int countByPostId(String postId);
}
