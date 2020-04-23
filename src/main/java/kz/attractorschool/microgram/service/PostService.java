package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.PostDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.PostImage;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final LikeRepo likeRepo;
    private final CommentRepo commentRepo;
    private final PostImageRepo postImageRepo;

    public Page<PostDTO> findPosts(Pageable pageable){
        Page<Post> posts = postRepo.findAll(pageable);
        updateNumbers(posts);
        return  posts.map(PostDTO::from);
    }
    public Page<PostDTO> findPostsByEmail(Pageable pageable, String email){
        Page<Post> posts = postRepo.findAllByUserEmail(pageable, email);
        updateNumbers(posts);
        return  posts.map(PostDTO::from);
    }
    public PostDTO findUserById(String postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find post with the id: " + postId));
        updateNumbers(post);
        return PostDTO.from(post);
    }
    public PostDTO post(MultipartFile poster,
                        String description,
                        Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String path = "../images/";
        File posterFile = new File(path + poster.getOriginalFilename());
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(posterFile);
            outputStream.write(poster.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Post post = Post.builder()
                .user(user)
                .dateTime(LocalDateTime.now())
                .id(UUID.randomUUID().toString())
                .image(poster.getOriginalFilename())
                .description(description)
                .build();

        postRepo.save(post);
        return PostDTO.from(post);
    }

    public PostDTO addPost(PostDTO postData, String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + username));

        PostImage postImage = postImageRepo.findById(postData.getImage())
                .orElseThrow(() -> new ResourceNotFoundException("Post Image with " + postData.getImage() + " doesn't exists!"));

        Post post = Post.builder()
                .user(user)
//                .image(postImage)
                .image(postData.getImage())
                .dateTime(postData.getDateTime())
                .id(postData.getId())
                .description(postData.getDescription())
                .build();

        postRepo.save(post);
        return PostDTO.from(post);
    }


    public boolean deletePost(String postId) {
        postRepo.deleteById(postId);
        return true;
    }
    private void updateNumbers(Post post){
        post.setNumOfLikes(likeRepo.countByPostId(post.getId()));
        post.setNumOfComments(commentRepo.countByPostId(post.getId()));
    }
    private void updateNumbers(Iterable<Post> posts){
        posts.forEach(post -> {
            post.setNumOfLikes(likeRepo.countByPostId(post.getId()));
            post.setNumOfComments(commentRepo.countByPostId(post.getId()));
        });
    }
}
