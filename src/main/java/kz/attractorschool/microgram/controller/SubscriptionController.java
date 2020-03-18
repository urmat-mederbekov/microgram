package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.SubscriptionDto;
import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.service.SubscriptionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/add/{follower}/{following}")
    public SubscriptionDto addUser(@RequestBody SubscriptionDto subscriptionData,
                           @PathVariable String follower,
                           @PathVariable String following) {
        return subscriptionService.addSubscription(subscriptionData, follower, following);
    }
}
