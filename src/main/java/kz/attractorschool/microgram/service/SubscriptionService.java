package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.SubscriptionDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Subscription;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.SubscriptionRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final UserRepo userRepo;

    public SubscriptionService(SubscriptionRepo subscriptionRepo, UserRepo userRepo) {
        this.subscriptionRepo = subscriptionRepo;
        this.userRepo = userRepo;
    }
    public SubscriptionDTO addSubscription(String followerName, String followingName){
        User follower = userRepo.findByUsername(followerName)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + followerName));

        User following = userRepo.findByUsername(followingName)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + followingName));

        Subscription subscription =  Subscription.builder()
                .follower(follower)
                .following(following)
                .dateTime(LocalDateTime.now())
                .id(UUID.randomUUID().toString())
                .build();

        subscriptionRepo.save(subscription);

        return SubscriptionDTO.from(subscription);
    }

    public boolean deleteSubscription(String subscriptionId) {

        subscriptionRepo.deleteById(subscriptionId);
        return true;
    }
}
