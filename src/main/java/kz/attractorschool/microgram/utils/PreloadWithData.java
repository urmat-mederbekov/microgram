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

    List<User> users = makeUsers();
    List<Post> posts = makePosts();
    List<Comment> comments = makeComments();
    List<Subscription> subscriptions = makeSubscriptions();
    List<Like> likes = makeLikes();

    @Bean
    CommandLineRunner initDatabase(UserRepo userRep, PostRepo postRep, CommentRepo commentRepo,
                                   LikeRepo likeRepo, SubscriptionRepo subscriptionRepo) {

        return (args) -> {
            userRep.deleteAll();
            postRep.deleteAll();
            likeRepo.deleteAll();
            subscriptionRepo.deleteAll();
            commentRepo.deleteAll();

            Stream.of(getComments())
                    .peek(comments -> comments.forEach(System.out::println))
                    .forEach(commentRepo::saveAll);
            Stream.of(getSubscriptions())
                    .peek(subscriptions -> subscriptions.forEach(System.out::println))
                    .forEach(subscriptionRepo::saveAll);
            Stream.of(getLikes())
                    .peek(likes -> likes.forEach(System.out::println))
                    .forEach(likeRepo::saveAll);
            Stream.of(getUsers())
                    .peek(users -> users.forEach(System.out::println))
                    .forEach(userRep::saveAll);
            Stream.of((getPosts()))
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
    private List<User> getUsers(){
        return users;
    }

    private List<Post> makePosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("", Generator.makeDescription(), getUsers().get(1)));
        posts.add(new Post("", Generator.makeDescription(), getUsers().get(0)));
        posts.add(new Post("", Generator.makeDescription(), getUsers().get(4)));
        return posts;
    }

    private List<Post> getPosts(){
        return posts;
    }

    private List<Like> makeLikes() {
        List<Like> likes = new ArrayList<>();
        likes.add(new Like(getUsers().get(2), getPosts().get(1)));
        likes.add(new Like(getUsers().get(3), getPosts().get(2)));
        likes.add(new Like(getUsers().get(3), getPosts().get(1)));
        return likes;
    }

    private List<Like> getLikes(){
        return likes;
    }

    private List<Subscription> makeSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(getUsers().get(0), getUsers().get(2)));
        subscriptions.add(new Subscription(getUsers().get(1), getUsers().get(3)));
        subscriptions.add(new Subscription(getUsers().get(0), getUsers().get(3)));
        return subscriptions;
    }

    private List<Subscription> getSubscriptions(){
        return subscriptions;
    }

    private List<Comment> makeComments(){
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(getPosts().get(0), getUsers().get(2), "wonderful"));
        comments.add(new Comment(getPosts().get(1), getUsers().get(3), "not bad"));
        comments.add(new Comment(getPosts().get(0), getUsers().get(3),"cool"));
        return comments;
    }
    private List<Comment> getComments(){
        return comments;
    }

}