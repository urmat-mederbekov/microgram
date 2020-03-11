package kz.attractorschool.microgram.model;

import org.springframework.data.repository.CrudRepository;

public interface SubscriptionToUserRepository extends CrudRepository<Subscription, String> {
}
