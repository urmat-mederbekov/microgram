package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.SubscriptionDto;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Subscription;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.SubscriptionRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final UserRepo userRepo;
    public SubscriptionService(SubscriptionRepo subscriptionRepo, UserRepo userRepo) {
        this.subscriptionRepo = subscriptionRepo;
        this.userRepo = userRepo;
    }
    public SubscriptionDto addSubscription(SubscriptionDto subscriptionData, String followerName, String followingName){
        User follower = userRepo.findByUsername(followerName)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + followerName));

        User following = userRepo.findByUsername(followingName)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + followingName));

        Subscription subscription =  Subscription.builder()
                .id(subscriptionData.getId())
                .dateTime(subscriptionData.getDateTime())
                .follower(follower)
                .following(following)
                .build();

        subscriptionRepo.save(subscription);

        follower.addFollowings(following);
        following.addFollowers(follower);

        return SubscriptionDto.from(subscription);
    }
}
