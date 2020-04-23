package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Slice<CommentDTO> findComments(Pageable pageable){
        return commentService.findAll(pageable);
    }

    @PostMapping(path = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO comment(@RequestBody CommentDTO commentData,
                                 @PathVariable String postId,
                                 Authentication authentication) {
        String commenterName = authentication.getName();
        return commentService.comment(commentData, postId, commenterName);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        if (commentService.deleteComment(commentId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
