package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.model.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String usersHandler(Model model) {
        model.addAttribute("users", User.getUsers());
        return "users";
    }
    @GetMapping("users/user")
    public String userHandler(@RequestParam("userId") String userId, Model model) {
        User user = userRepository.findById(userId).orElse(User.EMPTY);

        User.getUsers().stream().forEach(u -> u.updateNumOfPosts());
        Post.getPosts().stream().forEach(p -> p.updateNumOfLikes());
        model.addAttribute("user", user);
        model.addAttribute("posts", user.getPosts());

        return "user";
    }
}
