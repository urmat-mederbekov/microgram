package kz.attractorschool.microgram.utils;

import kz.attractorschool.microgram.model.*;
import kz.attractorschool.microgram.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@AllArgsConstructor
public class PreloadWithData {

    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner initDatabase(UserRepo userRep, PostRepo postRep, CommentRepo commentRepo,
                                   LikeRepo likeRepo, SubscriptionRepo subscriptionRepo) {

        return (args) -> {
            userRep.deleteAll();
            postRep.deleteAll();
            likeRepo.deleteAll();
            subscriptionRepo.deleteAll();
            commentRepo.deleteAll();

            List<User> userList = new ArrayList<>();
            userList.add(new User("thanos", "thanos@gmail.com", encoder.encode("thanos")));
            userList.add(new User("loki", "loki@gmail.com", encoder.encode("loki")));
            userList.add(new User("thor", "thor@gmail.com", encoder.encode("thor")));
            userList.add(new User("batman", "batman@gmail.com", encoder.encode("batman")));
            userList.add(new User("superman", "superman@gmail.com", encoder.encode("superman")));

            List<Post> postList = new ArrayList<>();
            postList.add(new Post(Generator.makeDescription(), userList.get(1), "uchiha.jpg"));
            postList.add(new Post(Generator.makeDescription(), userList.get(0), "wolf.jpg"));
            postList.add(new Post(Generator.makeDescription(), userList.get(4), "love.jpg"));

            List<Like> likeList = new ArrayList<>();
            likeList.add(new Like(userList.get(2), postList.get(1)));
            likeList.add(new Like(userList.get(3), postList.get(2)));
            likeList.add(new Like(userList.get(3), postList.get(1)));

            List<Subscription> subscriptionList = new ArrayList<>();
            subscriptionList.add(new Subscription(userList.get(0), userList.get(2)));
            subscriptionList.add(new Subscription(userList.get(1), userList.get(3)));
            subscriptionList.add(new Subscription(userList.get(0), userList.get(3)));

            List<Comment> commentList = new ArrayList<>();
            commentList.add(new Comment(postList.get(0), userList.get(2), "wonderful"));
            commentList.add(new Comment(postList.get(1), userList.get(3), "not bad"));
            commentList.add(new Comment(postList.get(0), userList.get(3),"cool"));

            Stream.of(commentList)
                    .peek(comments -> comments.forEach(System.out::println))
                    .forEach(commentRepo::saveAll);
            Stream.of(subscriptionList)
                    .peek(subscriptions -> subscriptions.forEach(System.out::println))
                    .forEach(subscriptionRepo::saveAll);
            Stream.of(likeList)
                    .peek(likes -> likes.forEach(System.out::println))
                    .forEach(likeRepo::saveAll);
            Stream.of(userList)
                    .peek(users -> users.forEach(System.out::println))
                    .forEach(userRep::saveAll);
            Stream.of((postList))
                    .peek(posts -> posts.forEach(System.out::println))
                    .forEach(postRep::saveAll);
        };
    }
}