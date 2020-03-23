package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.PostDTO;
import kz.attractorschool.microgram.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public PostDTO findPostById(@PathVariable String postId){
        return postService.findUserById(postId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{username}")
    public PostDTO addPost(@RequestBody PostDTO postData, @PathVariable String username) {
        return postService.addPost(postData, username);
    }
    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String postId) {
        if (postService.deletePost(postId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
