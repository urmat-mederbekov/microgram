package kz.attractorschool.microgram.utils;

import kz.attractorschool.microgram.model.*;
import kz.attractorschool.microgram.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class PreloadWithData {

    @Bean
    CommandLineRunner initDatabase(UserRepo userRep, PostRepo postRep, CommentRepo commentRepo,
                                   LikeRepo likeRepo, SubscriptionRepo subscriptionRepo) {

        return (args) -> {
            userRep.deleteAll();
            postRep.deleteAll();
            likeRepo.deleteAll();
            subscriptionRepo.deleteAll();
            commentRepo.deleteAll();

            Stream.of(makeComments())
                    .peek(comments -> comments.forEach(System.out::println))
                    .forEach(commentRepo::saveAll);
            Stream.of(makeSubscriptions())
                    .peek(subscriptions -> subscriptions.forEach(System.out::println))
                    .forEach(subscriptionRepo::saveAll);
            Stream.of(makeLikes())
                    .peek(likes -> likes.forEach(System.out::println))
                    .forEach(likeRepo::saveAll);
            Stream.of(makeUsers())
                    .peek(users -> users.forEach(System.out::println))
                    .forEach(userRep::saveAll);
            Stream.of((makePosts()))
                    .peek(posts -> posts.forEach(System.out::println))
                    .forEach(postRep::saveAll);
        };
    }

    private List<User> makeUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("thanos", "thanos@gmail.com", "thanos"));
        users.add(new User("loki", "loki@gmail.com", "loki"));
        users.add(new User("thor", "thor@gmail.com", "thor"));
        users.add(new User("batman", "batman@gmail.com", "batman"));
        users.add(new User("superman", "superman@gmail.com", "superman"));
        return users;
    }

    private List<Post> makePosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("", Generator.makeDescription(), makeUsers().get(1)));
        posts.add(new Post("", Generator.makeDescription(), makeUsers().get(0)));
        posts.add(new Post("", Generator.makeDescription(), makeUsers().get(4)));
        return posts;
    }
    private List<Like> makeLikes() {
        List<Like> likes = new ArrayList<>();
        likes.add(new Like(makeUsers().get(2), makePosts().get(1)));
        likes.add(new Like(makeUsers().get(3), makePosts().get(2)));
        likes.add(new Like(makeUsers().get(3), makePosts().get(1)));
        return likes;
    }

    private List<Subscription> makeSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(makeUsers().get(0), makeUsers().get(2)));
        subscriptions.add(new Subscription(makeUsers().get(1), makeUsers().get(3)));
        subscriptions.add(new Subscription(makeUsers().get(0), makeUsers().get(3)));
        return subscriptions;
    }
    private List<Comment> makeComments(){
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(makePosts().get(0), makeUsers().get(2), "wonderful"));
        comments.add(new Comment(makePosts().get(1), makeUsers().get(3), "not bad"));
        comments.add(new Comment(makePosts().get(0), makeUsers().get(3),"cool"));
        return comments;
    }
}