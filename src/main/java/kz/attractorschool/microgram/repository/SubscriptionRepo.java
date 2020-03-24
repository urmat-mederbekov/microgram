package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepo extends CrudRepository<Subscription, String> {
    int countByFollowerEmail(String email);
    int countByFollowingEmail(String email);
    void deleteByFollowerUsername(String username);
}
