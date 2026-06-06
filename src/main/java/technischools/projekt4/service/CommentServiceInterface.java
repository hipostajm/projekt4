package technischools.projekt4.service;

import technischools.projekt4.model.Comment;

import java.util.List;

public interface CommentServiceInterface {
    List<Comment> getCommentsByPostId(Long postId);
    Comment createComment(Comment comment);
    void deleteComment(Long id);
    Comment getCommentById(Long id);
}
