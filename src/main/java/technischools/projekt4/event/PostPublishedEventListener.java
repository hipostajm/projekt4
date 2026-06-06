package technischools.projekt4.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import technischools.projekt4.config.AuthorStatsHolder;
import technischools.projekt4.model.Post;

@Component
public class PostPublishedEventListener {
    private final AuthorStatsHolder authorStatsHolder;

    public PostPublishedEventListener(AuthorStatsHolder authorStatsHolder) {
        this.authorStatsHolder = authorStatsHolder;
    }

    @EventListener
    public void onPostPublished(PostPublishedEvent event) {
        Post post = event.getPost();
        String author = post.getAuthor();
        authorStatsHolder.incrementPostCount(author);

        System.out.println("Post published by " + author + ": " + post.getTitle());
    }
}
