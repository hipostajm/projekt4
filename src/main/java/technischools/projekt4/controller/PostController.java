package technischools.projekt4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technischools.projekt4.model.Post;
import technischools.projekt4.model.PostCategory;
import technischools.projekt4.service.PostServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController extends BaseController {

    private final PostServiceInterface postService;

    public PostController(PostServiceInterface postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> GetPosts() {
        return ResponseEntity.status(200).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> GetPost(@PathVariable Long id) {
       return ResponseEntity.status(200).body(postService.getPostById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> GetSearchPosts(@RequestParam String keyword, @RequestParam PostCategory category) {
        return ResponseEntity.status(200).body(postService.getPostsByTitleOrCategory(keyword, category));
    }

    @GetMapping("/pinned")
    public ResponseEntity<List<Post>> GetPinnedPosts() {
        return ResponseEntity.status(200).body(postService.getPinnedPosts());
    }

    @PostMapping
    public ResponseEntity<Post> CreatePost(@RequestBody Post post) {
        return ResponseEntity.status(201).body(postService.createPost(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> UpdatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.status(200).body(postService.updatePost(id, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
