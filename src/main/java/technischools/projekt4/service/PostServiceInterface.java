package technischools.projekt4.service;

import technischools.projekt4.model.Post;
import technischools.projekt4.model.PostCategory;

import java.util.List;

public interface PostServiceInterface {
    Post getPostById(Long id);

    List<Post> getAllPosts();

    List<Post> getPostsByTitleOrCategory(String title, PostCategory category);

    List<Post> getPinnedPosts();

    Post createPost(Post post);

    Post updatePost(Long id, Post post);

    Post deletePost(Long id);
}
