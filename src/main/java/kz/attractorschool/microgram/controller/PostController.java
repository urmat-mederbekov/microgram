package kz.attractorschool.microgram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    @GetMapping
    public String findPosts(){
        return "posts";
    }

}
