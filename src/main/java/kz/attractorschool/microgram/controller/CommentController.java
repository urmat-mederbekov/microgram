package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.service.CommentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/{postId}/{commenterName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO addComment(@RequestBody CommentDTO commentData,
                           @PathVariable String postId,
                           @PathVariable String commenterName) {
        return commentService.addComment(commentData, postId, commenterName);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        if (commentService.deleteComment(commentId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
