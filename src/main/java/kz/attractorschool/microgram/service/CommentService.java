package kz.attractorschool.microgram.service;

import kz.attractorschool.microgram.dto.CommentDTO;
import kz.attractorschool.microgram.exception.ResourceNotFoundException;
import kz.attractorschool.microgram.model.Comment;
import kz.attractorschool.microgram.model.Post;
import kz.attractorschool.microgram.model.User;
import kz.attractorschool.microgram.repository.CommentRepo;
import kz.attractorschool.microgram.repository.PostRepo;
import kz.attractorschool.microgram.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    public Slice<CommentDTO> findAll(Pageable pageable){
        Slice<Comment> comments = commentRepo.findAll(pageable);
        return comments.map(CommentDTO::from);
    }

    public Slice<CommentDTO> findCommentByPostId(Pageable pageable, String postId){

        Slice<Comment> comments = commentRepo.findAllByPostId(pageable, postId);
        return comments.map(CommentDTO::from);
    }

    public CommentDTO comment(CommentDTO commentData, String postId, String commenterUsername) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the id: " + postId));

        User user = userRepo.findByUsername(commenterUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find user with the name: " + commenterUsername));

        Comment comment = Comment.builder()
                .commenter(user)
                .post(post)
                .text(commentData.getText())
                .path(commentData.getPath())
                .dateTime(commentData.getDateTime())
                .id(commentData.getId())
                .build();

        commentRepo.save(comment);

        return CommentDTO.from(comment);
    }

    public boolean deleteComment(String commentId) {
        commentRepo.deleteById(commentId);
        return true;
    }
}
