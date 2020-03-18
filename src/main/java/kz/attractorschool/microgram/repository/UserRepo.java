package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findAllByUsernameNotContains(Pageable pageable, String username);
    void deleteByUsername(String username);
}
