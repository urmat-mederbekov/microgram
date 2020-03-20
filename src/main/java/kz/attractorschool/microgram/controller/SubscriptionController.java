package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.SubscriptionDTO;
import kz.attractorschool.microgram.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/add/{follower}/{following}")
    public SubscriptionDTO addUser(@PathVariable String followerName, @PathVariable String followingName) {
        return subscriptionService.addSubscription(followerName, followingName);
    }
    @DeleteMapping("/delete/{subscriptionId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String subscriptionId) {
        if (subscriptionService.deleteSubscription(subscriptionId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
