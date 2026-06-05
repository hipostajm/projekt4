package technischools.projekt4.service;

import org.springframework.stereotype.Service;
import technischools.projekt4.exception.PostNotFound;
import technischools.projekt4.model.Post;
import technischools.projekt4.repository.PostRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PostService implements PostServiceInterface {
    private final PostRepository repository;
    public PostService(PostRepository repository) {
       this.repository = repository;
    }

    public Post getPostById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PostNotFound(id));
    }

    public List<Post> getAllPosts() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    public List<Post> getPostsByTitleOrCategory(String title, String category) {
        return repository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(title, category);
    }

    public List<Post> getPinnedPosts() {
        return repository.findByPinnedIsTrue();
    }

    public Post createPost(Post post) {
        return repository.save(post);
    }

    public  Post updatePost(Long id, Post post) {
        Post oldPost = this.getPostById(post.getId());
        oldPost.setTitle(post.getTitle());
        oldPost.setContent(post.getCategory());
        return repository.save(oldPost);
    }

    public Post deletePost(Long id) {
        Post post = this.getPostById(id);
        repository.deleteById(id);
        return post;
    }
}

