package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Like;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepo extends CrudRepository<Like, String> {
}

