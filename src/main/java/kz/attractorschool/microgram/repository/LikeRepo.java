package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LikeRepo extends PagingAndSortingRepository<Like, String> {
    int countByPostId(String postId);
    Page<Like> findAllByPostId(Pageable pageable, String postId);
}

