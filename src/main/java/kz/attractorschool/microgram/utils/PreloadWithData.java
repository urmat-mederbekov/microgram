package kz.attractorschool.microgram.utils;

import kz.attractorschool.microgram.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Configuration
public class PreloadWithData {

    @Bean
    CommandLineRunner initLikedDatabase(LikeRepository likeRep) {
        likeRep.deleteAll();

        Post.getPosts().get(2).addLike(Like.getLikes().get(0));
        Post.getPosts().get(1).addLike(Like.getLikes().get(2));
        return (args) -> Stream.of(Like.getLikes())
                .peek(likes -> likes.forEach(System.out::println))
                .forEach(likeRep::saveAll);
    }
    @Bean
    CommandLineRunner initPostDatabase(PostRepository postRep) {
        postRep.deleteAll();

        Post.getPosts().stream().forEach(post -> post.updateNumOfLikes());

        return (args) -> Stream.of((Post.getPosts()))
                .peek(posts -> posts.forEach(System.out::println))
                .forEach(postRep::saveAll);
    }

    @Bean
    CommandLineRunner initUserDatabase(UserRepository userRep) {
        userRep.deleteAll();
        Post post = Post.getPosts().get(2);
        User.getUsers().get(0).addPost(post);
        Post post1 = Post.getPosts().get(1);
        User.getUsers().get(0).addPost(post1);

        User.getUsers().stream().forEach(user -> user.updateNumOfPosts());

        return (args) -> Stream.of(User.getUsers())
                .peek(users -> users.forEach(System.out::println))
                .forEach(userRep::saveAll);
    }

    //    @Bean
//    CommandLineRunner initCommentDatabase(CommentRepository commentRep) {
//        commentRep.deleteAll();
//
//        return  (args) -> Stream.of(makeComments())
//                .peek(System.out::println)
//                .forEach(commentRep::save);
//    }

//    @Bean
//    CommandLineRunner initSubscriptionDatabase(SubscriptionToUserRepository subscriptionToUserRep) {
//        subscriptionToUserRep.deleteAll();
//
//        return  (args) -> Stream.of(makeSubscriptionToUser())
//                .peek(System.out::println)
//                .forEach(subscriptionToUserRep::save);
//    }
}