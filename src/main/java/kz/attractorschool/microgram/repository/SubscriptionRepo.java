package kz.attractorschool.microgram.repository;

import kz.attractorschool.microgram.model.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepo extends CrudRepository<Subscription, String> {
    int countByFollowerId(String id);
    int countByFollowingId(String id);
}
