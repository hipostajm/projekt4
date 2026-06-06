package technischools.projekt4.service;

import java.util.Map;

public interface StatsServiceInterface {
    Map<String, Integer> getPostCountPerAuthor();
    Map<String, Integer> getPostCountPerCategory();
}
