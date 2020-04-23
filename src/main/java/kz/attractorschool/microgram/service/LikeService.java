package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Like;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.LikeRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepo likeRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public Page<LikeDTO> findLikes(Pageable pageable){
        return likeRepo.findAll(pageable).map(LikeDTO::from);
    }
    public Page<LikeDTO> findOtherLikes(Pageable pageable, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return likeRepo.findAllByLikerEmail(pageable, user.getEmail()).map(LikeDTO::from);
    }

    public Page<LikeDTO> findLikesByPostId(Pageable pageable, String postId){
        Page<Like> likes = likeRepo.findAllByPostId(pageable, postId);
        return likes.map(LikeDTO::from);
    }

    public LikeDTO addLike(String postId, String likerUsername) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the id: " + postId));

        User user = userRepo.findByUsername(likerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + likerUsername));

        Like like= Like.builder()
                .liker(user)
                .post(post)
                .dateTime(LocalDateTime.now())
                .id(UUID.randomUUID().toString())
                .build();

        likeRepo.save(like);

        return LikeDTO.from(like);
    }

    public boolean deleteLike(String likeId) {
        likeRepo.deleteById(likeId);
        return true;
    }
}
