package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public LikeDTO like(@PathVariable String postId,
                           Authentication authentication) {
        String likerName = authentication.getName();
        return likeService.addLike(postId, likerName);
    }
    @DeleteMapping("{likeId}")
    public ResponseEntity<Void> unlike(@PathVariable String likeId) {
        if (likeService.deleteLike(likeId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
