package technischools.projekt4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technischools.projekt4.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
