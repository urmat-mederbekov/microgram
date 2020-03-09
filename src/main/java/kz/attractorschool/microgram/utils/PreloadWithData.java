package kz.attractorschool.microgram.utils;

import kz.attractorschool.microgram.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Configuration
public class PreloadWithData {
    LocalDateTime dateTime = LocalDateTime.now();
    @Bean
    CommandLineRunner initUserDatabase(CommentRepository commentRep, LikedRepository likedRep,
                                   PostRepository postRep, SubscriptionToUserRepository subscriptionToUserRep,
                                   UserRepository userRep) {
        userRep.deleteAll();

        return  (args) -> Stream.of(makeUsers())
                .peek(System.out::println)
                .forEach(userRep::save);
    }
    @Bean
    CommandLineRunner initCommentDatabase(CommentRepository commentRep) {
        commentRep.deleteAll();

        return  (args) -> Stream.of(makeComments())
                .peek(System.out::println)
                .forEach(commentRep::save);
    }
    @Bean
    CommandLineRunner initLikedDatabase(LikedRepository likedRep) {
        likedRep.deleteAll();

        return  (args) -> Stream.of(makeLikeds())
                .peek(System.out::println)
                .forEach(likedRep::save);
    }
    @Bean
    CommandLineRunner initPostDatabase(PostRepository postRep) {
        postRep.deleteAll();

        return  (args) -> Stream.of(makePosts())
                .peek(System.out::println)
                .forEach(postRep::save);
    }
    @Bean
    CommandLineRunner initSubscriptionDatabase(SubscriptionToUserRepository subscriptionToUserRep) {
        subscriptionToUserRep.deleteAll();

        return  (args) -> Stream.of(makeSubscriptionToUser())
                .peek(System.out::println)
                .forEach(subscriptionToUserRep::save);
    }

    private User[] makeUsers() {
        return new User[]{
                new User("1","thanos", "thanos@gmail.com", "thanos", 10, 6,324),
                new User("2","loki", "loki@gmail.com", "loki", 150, 57,444),
                new User("3","thor", "thor@gmail.com", "thor", 123, 565,546),
                new User("4","batman", "batman@gmail.com", "batman", 20, 3567,656),
                new User("5","superman", "superman@gmail.com", "superman", 40, 9085,633)
        };
    }
    private Comment[] makeComments() {
        return new Comment[]{
                new Comment("1","thanos is god", dateTime),
                new Comment("2","loki didn't die", dateTime.minusDays(4)),
                new Comment("5","superman is lit", dateTime.minusMinutes(5))
        };
    }
    private Post[] makePosts() {
        return new Post[]{
                new Post("1","", "Thanos is mad", dateTime),
                new Post("2","", "Loki is murdered by Thanos", dateTime.minusDays(4)),
                new Post("5","", "Superman sucks", dateTime.minusMinutes(5))
        };
    }
    private SubscriptionToUser[] makeSubscriptionToUser() {
        return new SubscriptionToUser[]{
                new SubscriptionToUser("1", "Loki", "Thanos", dateTime),
                new SubscriptionToUser("2","Superman", "Loki", dateTime.minusDays(4)),
                new SubscriptionToUser("5","Thor", "Superman", dateTime.minusMinutes(5))
        };
    }
    private Liked[] makeLikeds() {
        return new Liked[]{
                new Liked("1", "Loki", "Thanos", dateTime),
                new Liked("2","Superman", "I can fly", dateTime.minusDays(4)),
                new Liked("5","Thor", "loki is dead", dateTime.minusMinutes(5))
        };
    }
}