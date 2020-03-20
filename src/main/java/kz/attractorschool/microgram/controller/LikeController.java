package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.LikeDTO;
import kz.attractorschool.microgram.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}/{likerName}/add")
    public LikeDTO addLike(@PathVariable String postId,
                           @PathVariable String likerName) {
        return likeService.addLike(postId, likerName);
    }
    @DeleteMapping("/delete/{likeId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String likeId) {
        if (likeService.deleteLike(likeId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
