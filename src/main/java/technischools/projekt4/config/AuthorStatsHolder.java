package technischools.projekt4.config;

import java.util.HashMap;
import java.util.Map;

public class AuthorStatsHolder {
    private final Map<String, Integer> authorPostCount = new HashMap<>();

    public void incrementPostCount(String author) {
        authorPostCount.put(author, authorPostCount.getOrDefault(author, 0) + 1);
    }

    public Integer getPostCount(String author) {
        return authorPostCount.getOrDefault(author, 0);
    }

    public Map<String, Integer> getAllStats() {
        return new HashMap<>(authorPostCount);
    }
}
