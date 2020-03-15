package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, String> {
}
