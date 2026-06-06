package technischools.projekt4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technischools.projekt4.model.Comment;
import technischools.projekt4.model.Post;
import technischools.projekt4.service.CommentService;
import technischools.projekt4.service.CommentServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController {
    private final CommentServiceInterface commentService;

    public CommentController(CommentServiceInterface commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> GetCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.status(200).body(commentService.getCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<Comment> CreateComment(@RequestBody Comment comment) {
        return ResponseEntity.status(201).body(commentService.createComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
