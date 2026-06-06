package technischools.projekt4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technischools.projekt4.service.StatsServiceInterface;

import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController extends BaseController {
    private final StatsServiceInterface statsService;

    public StatsController(StatsServiceInterface statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/posts-per-author")
    public ResponseEntity<Map<String, Integer>> GetPostCountPerAuthor() {
        return ResponseEntity.status(200).body(statsService.getPostCountPerAuthor());
    }

    @GetMapping("/posts-per-category")
    public ResponseEntity<Map<String, Integer>> GetPostCountPerCategory() {
        return ResponseEntity.status(200).body(statsService.getPostCountPerCategory());
    }
}
