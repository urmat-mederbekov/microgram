package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.annotations.ApiPageable;
import kz.attractorschool.microgram.dto.PostDTO;
import kz.attractorschool.microgram.dto.UserDTO;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.service.PostService;
import kz.attractorschool.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @ApiPageable
    @GetMapping
    public Slice<UserDTO> findUsers(@ApiIgnore Pageable pageable) {
        return userService.findUsers(pageable);
    }

    @ApiPageable
    @GetMapping("/user/posts")
    public Page<PostDTO> findPostsByEmail(@ApiIgnore Pageable pageable, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return postService.findPostsByEmail(pageable, user.getEmail());
    }

    @ApiPageable
    @GetMapping("/others")
    public Slice<UserDTO> findOtherUsers(@ApiIgnore Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.findOtherUsers(pageable, user.getUsername());
    }
    @GetMapping("/explore")
    public List<PostDTO> findOtherPosts(@ApiIgnore Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.findOtherPosts(pageable, user.getUsername());
    }
    @GetMapping("/story")
    public List<PostDTO> findPostsBasedFollowings(@ApiIgnore Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.findPostsBasedFollowings(pageable, user.getEmail());
    }

    @GetMapping("/username")
    public UserDTO findUserByUsername(Authentication authentication) {
        String username = authentication.getName();
        return userService.findUserByUsername(username);
    }

    @GetMapping("/email/")
    public UserDTO findUserByEmail(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userService.findUserByEmail(user.getEmail());
    }

    @GetMapping("/email/exist")
    public String existUserByEmail(Authentication authentication){
    User user = (User) authentication.getPrincipal();
    return userService.existsUserByEmail(user.getEmail());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addUser(@RequestBody UserDTO userData) {
        return userService.addUser(userData);
    }

    @DeleteMapping("/username")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        String username = authentication.getName();
        if (userService.deleteUser(username))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
