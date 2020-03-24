package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.annotations.ApiPageable;
import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.dto.PostDTO;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

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
