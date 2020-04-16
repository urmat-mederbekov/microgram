package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.annotations.ApiPageable;
import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.dto.PostDTO;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.service.CommentService;
import kz.attractorschool.microgram.service.LikeService;
import kz.attractorschool.microgram.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/rest/posts")
@AllArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostRepo postRepo;

    @ApiPageable
    @GetMapping
    public Page<PostDTO> findPosts(@ApiIgnore Pageable pageable){
        return postService.findPosts(pageable);
    }

    @ApiPageable
    @GetMapping("/{postId}/likes")
    public Page<LikeDTO> findLikesByPostId(@ApiIgnore Pageable pageable, @PathVariable String postId){
        return likeService.findLikesByPostId(pageable, postId);
    }

    @ApiPageable
    @GetMapping("/{postId}/comments")
    public Slice<CommentDTO> findCommentsByPostId(@ApiIgnore Pageable pageable, @PathVariable String postId){
        return commentService.findCommentByPostId(pageable, postId);
    }

    @GetMapping("/{postId}")
    public PostDTO findPostById(@PathVariable String postId){
        return postService.findUserById(postId);
    }

    @PostMapping("/post")
    public String post(
//            @RequestParam String id,
                        @RequestParam MultipartFile poster,
                        @RequestParam String description,
                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        System.out.println(poster.getOriginalFilename());
        System.out.println(description);
        String path = "../images/";
        File posterFile = new File(path + poster.getOriginalFilename());
        System.out.println(posterFile.getPath());
        FileOutputStream outputStream = new FileOutputStream(posterFile);
        outputStream.write(poster.getBytes());
        outputStream.close();
        Post post = Post.builder()
                .user(user)
                .dateTime(LocalDateTime.now())
                .image(poster.getOriginalFilename())
                .id(UUID.randomUUID().toString())
                .description(description)
                .build();

        postRepo.save(post);
        return "success";
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
        String path = "../images";
        try {
            InputStream is = new FileInputStream(new File(path) + "/" + name);
            return ResponseEntity
                    .ok()
                    .contentType(name.toLowerCase().contains(".png")? MediaType.IMAGE_PNG:MediaType.IMAGE_JPEG)
                    .body(StreamUtils.copyToByteArray(is));
        } catch (Exception e) {
            InputStream is = null;
            try {
                is = new FileInputStream(new File(path) + "/" + name);
                return ResponseEntity
                        .ok()
                        .contentType(name.toLowerCase().contains(".png")?MediaType.IMAGE_PNG:MediaType.IMAGE_JPEG)
                        .body(StreamUtils.copyToByteArray(is));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PostDTO post(@RequestBody PostDTO postData, Authentication authentication) {
        String username = authentication.getName();
        return postService.addPost(postData, username);
    }



    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        if (postService.deletePost(postId))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
