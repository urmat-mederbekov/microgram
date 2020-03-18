package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.PostDTO;
import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public PostDTO addPost(PostDTO postData, String username) {
        Post post = Post.builder()
                .id(postData.getId())
                .image(postData.getImage())
                .description(postData.getDescription())
                .numOfLikes(postData.getNumOfLikes())
                .likes(postData.getLikes())
                .build();
        User userData = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + username));
        System.out.println(username + " before " + userData.getPosts().size());
        userData.addPost(post);

        System.out.println(username + " after " + userData.getPosts().size());
        postRepo.save(post);
        return PostDTO.from(post);
    }

}
