package technischools.projekt4.repository;

import org.springframework.data.repository.CrudRepository;
import technischools.projekt4.model.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long>{
    List<Post> findByCategoryOrderByCreatedAtDesc(String category);
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    List<Post> findByPinnedIsTrue();
}
