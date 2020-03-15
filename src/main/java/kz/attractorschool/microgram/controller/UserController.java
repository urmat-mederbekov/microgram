package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.UserRepo;
import org.hibernate.validator.constraints.EAN;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    Sort sort = Sort.by(Sort.Order.asc("accountName"));

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String homeGet() {
        return "home";
    }
    @GetMapping("/home")
    public String homeGet2() {
        return "home";
    }

    @GetMapping("/hello")
    public String hellGet() {
        return "hello";
    }
    @GetMapping("/login")
    public String loginGet() {
        return "login";
    }
    @GetMapping("/users/name/{name}")
    public String getUserByName(@PathVariable("name") String name, Model model) {
        model.addAttribute("users", userRepo.findAllByAccountName(name, sort));
        return "users";
    }
    @GetMapping("/users/otherpost/{name}")
    public String getUserByNameExceptYou(@PathVariable("name") String name, Model model) {
        model.addAttribute("users", userRepo.findAllByAccountNameNotContains(name, sort));
        return "otherPosts";
    }
    @GetMapping("/users/explore/{name}")
    public String getFollowingsPost(@PathVariable("name") String name,Model model) {
        System.out.println("explore");
//        List<User> x = User.getUsers()
//                .stream()
//                .filter(user -> user.isLoggedIn() != true)
//                .collect(Collectors.toList());
//        for(User user: User.getUsers()){
//            for(User user1: user.getFollowers()){
//                if (user1.equals(name)){
//                    var x =
//                }
//            }
//        }
        model.addAttribute("users", userRepo.findAllByAccountName(name, sort));
        return "otherPosts";
    }
    @GetMapping("/users/email/{email}")
    public String getUserByEmail(@PathVariable("email") String email, Model model) {
        Sort sort = Sort.by(Sort.Order.asc("email"));
        model.addAttribute("users", userRepo.findAllByEmail(email, sort));
        return "users";
    }
    @GetMapping("/users/exist/{email}")
    public String checkUserExistenceByEmail(@PathVariable("email") String email, Model model) {
        model.addAttribute("isExist", userRepo.existsUserByEmail(email));
        return "existence";
    }

    @GetMapping("/users")
    public String usersGet(Model model) {
        model.addAttribute("users", User.getUsers());
        return "users";
    }
    @GetMapping("users/user")
    public String userGet(@RequestParam("userId") String userId, Model model) {
        User user = userRepo.findById(userId).orElse(User.EMPTY);

        model.addAttribute("user", user);
        return "user";
    }
}
