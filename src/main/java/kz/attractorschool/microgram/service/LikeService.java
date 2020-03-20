package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Like;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.LikeRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LikeService {
    private final LikeRepo likeRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public LikeService(LikeRepo likeRepo, PostRepo postRepo, UserRepo userRepo) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public LikeDTO addLike(String postId, String likerUsername) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the id: " + postId));

        User user = userRepo.findByUsername(likerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + likerUsername));

        Like like= Like.builder()
                .id(UUID.randomUUID().toString())
                .liker(user)
                .post(post)
                .dateTime(LocalDateTime.now())
                .build();

        likeRepo.save(like);

        return LikeDTO.from(like);
    }

    public boolean deleteLike(String likeId) {
        likeRepo.deleteById(likeId);
        return true;
    }
}
