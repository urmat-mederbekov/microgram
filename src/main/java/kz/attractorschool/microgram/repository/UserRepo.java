package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, String> {
    List<User> findAllByAccountName(String name, Sort sort);
    List<User> findAllByEmail(String email, Sort sort);
    boolean existsUserByEmail(String email);
    List<User> findAllByAccountNameNotContains(String name, Sort sort);
    List<User> findAllByFollowingsNotContains(List<User> users, Sort sort);

}
