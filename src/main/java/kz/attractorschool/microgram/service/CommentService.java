package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Comment;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.CommentRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public CommentService(CommentRepo commentRepo, PostRepo postRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }
    public CommentDTO addComment(CommentDTO commentData, String postId, String commenterUsername) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the id: " + postId));

        User user = userRepo.findByUsername(commenterUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + commenterUsername));

        Comment comment = Comment.builder()
                .id(commentData.getId())
                .commenter(user)
                .post(post)
                .dateTime(commentData.getDateTime())
                .text(commentData.getText())
                .path(commentData.getPath())
                .build();

        commentRepo.save(comment);

        return CommentDTO.from(comment);
    }
    public boolean deleteComment(String commentId) {
        commentRepo.deleteById(commentId);
        return true;
    }
}
