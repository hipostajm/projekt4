package technischools.projekt4.service;

import org.springframework.stereotype.Service;
import technischools.projekt4.config.AuthorStatsHolder;
import technischools.projekt4.model.Post;
import technischools.projekt4.repository.PostRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StatsService implements StatsServiceInterface {
    private final AuthorStatsHolder authorStatsHolder;
    final private PostRepository repository;

    public StatsService(AuthorStatsHolder authorStatsHolder, PostRepository repository) {
        this.authorStatsHolder = authorStatsHolder;
        this.repository = repository;
    }

    @Override
    public Map<String, Integer> getPostCountPerAuthor() {
        return authorStatsHolder.getAllStats();
    }

    @Override
    public Map<String, Integer> getPostCountPerCategory() {
        List<Post> posts = StreamSupport.stream(repository.findAll().spliterator(), false).toList();
        return posts.stream().collect(Collectors.groupingBy(p -> p.getCategory().name(), Collectors.summingInt(p -> 1)));
    }
}
