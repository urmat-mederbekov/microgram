package kz.attractorschool.microgram.controller;

import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Comment;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.CommentRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import kz.attractorschool.microgram.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;

    @GetMapping
    public Slice<CommentDTO> findComments(Pageable pageable){
        return commentService.findAll(pageable);
    }

    @PostMapping(path = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO comment(@RequestBody CommentDTO commentData,
                                 @PathVariable String postId,
                                 Authentication authentication) {
        String commenterName = authentication.getName();
        return commentService.addComment(commentData, postId, commenterName);
    }
    @PostMapping(path = "/comment")
    public CommentDTO comment(@RequestParam String postId,
                              @RequestParam String userId,
                              @RequestParam String text,
                              Authentication authentication
    ) {
        String commenterName = authentication.getName();

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the id: " + postId));
        User user = userRepo.findByUsername(commenterName)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + commenterName));

        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .commenter(user)
                .post(post)
                .dateTime(LocalDateTime.now())
                .text(text)
                .build();

        commentRepo.save(comment);
        return CommentDTO.from(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        if (commentService.deleteComment(commentId))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
