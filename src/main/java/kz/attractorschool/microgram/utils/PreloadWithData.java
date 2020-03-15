package kz.attractorschool.microgram.utils;

import kz.attractorschool.microgram.model.*;
import kz.attractorschool.microgram.repository.LikeRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.SubscriptionRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class PreloadWithData {

    @Bean
    CommandLineRunner initLikedDatabase(LikeRepo likeRep) {
        likeRep.deleteAll();

        Post.getPosts().get(2).addLike(Like.getLikes().get(0));
        Post.getPosts().get(1).addLike(Like.getLikes().get(2));
        return (args) -> Stream.of(Like.getLikes())
                .peek(likes -> likes.forEach(System.out::println))
                .forEach(likeRep::saveAll);
    }

    @Bean
    CommandLineRunner initPostDatabase(PostRepo postRep) {
        postRep.deleteAll();

//        Post.getPosts().stream().forEach(Post::updateNumOfLikes);

        return (args) -> Stream.of((Post.getPosts()))
                .peek(posts -> posts.forEach(System.out::println))
                .forEach(postRep::saveAll);
    }

    @Bean
    CommandLineRunner initUserDatabase(UserRepo userRep) {
        userRep.deleteAll();
        Post post = Post.getPosts().get(2);
        User.getUsers().get(0).addPost(post);
        Post post1 = Post.getPosts().get(1);
        User.getUsers().get(0).addPost(post1);

        User.getUsers().get(2).addFollowers(User.getUsers().get(4));
//        User.getUsers().get(4).addFollowings(User.getUsers().get(2));
//        User.subscribe(0,2);

        User.getUsers().get(0).addFollowers(User.getUsers().get(1));
//        User.getUsers().get(1).addFollowings(User.getUsers().get(0));
//        User.subscribe(1,3);
////
        User.getUsers().get(3).addFollowers(User.getUsers().get(4));
//        User.getUsers().get(4).addFollowings(User.getUsers().get(3));
//        User.subscribe(3,0);
        User.getUsers().get(3).addFollowers(User.getUsers().get(0));
//        User.getUsers().get(0).addFollowings(User.getUsers().get(3));
//        User.subscribe(0,4);

        return (args) -> Stream.of(User.getUsers())
                .peek(users -> users.forEach(System.out::println))
                .forEach(userRep::saveAll);
    }

    @Bean
    CommandLineRunner initSubscriptionDatabase(SubscriptionRepo subscriptionRepo) {
        subscriptionRepo.deleteAll();

        return  (args) -> Stream.of(Subscription.getSubscriptions())
                .peek(subscriptions -> subscriptions.forEach(System.out::println))
                .forEach(subscriptionRepo::saveAll);
    }

    //    @Bean
//    CommandLineRunner initCommentDatabase(CommentRepository commentRep) {
//        commentRep.deleteAll();
//
//        return  (args) -> Stream.of(makeComments())
//                .peek(System.out::println)
//                .forEach(commentRep::save);
//    }
}