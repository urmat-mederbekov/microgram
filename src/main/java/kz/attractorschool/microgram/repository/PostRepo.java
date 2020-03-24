package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepo extends PagingAndSortingRepository<Post, String> {
    int countByUserEmail(String email);
    Page<Post> findAllByUserEmail(Pageable pageable, String email);
}
