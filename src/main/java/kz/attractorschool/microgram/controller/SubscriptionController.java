package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.SubscriptionDto;
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

    @PostMapping("/{followerName}/{followingName}")
    public SubscriptionDto addUser(@PathVariable String followerName, @PathVariable String followingName) {
        return subscriptionService.addSubscription(followerName, followingName);
    }
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String subscriptionId) {
        if (subscriptionService.deleteSubscription(subscriptionId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
