package technischools.projekt4.service;

import org.springframework.stereotype.Service;
import technischools.projekt4.exception.ResourceNotFoundException;
import technischools.projekt4.model.Comment;
import technischools.projekt4.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService implements CommentServiceInterface {
    private CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

    public Comment createComment(Comment comment) {
        return repository.save(comment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }

    public Comment getCommentById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }
}
