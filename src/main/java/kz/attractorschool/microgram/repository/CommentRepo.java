package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepo extends PagingAndSortingRepository<Comment, String> {
    int countByPostId(String postId);
    Slice<Comment> findAllByPostId(Pageable pageable, String postId);
}
