package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.PostImage;
import org.springframework.data.repository.CrudRepository;

public interface PostImageRepo extends CrudRepository<PostImage, String> {
}
