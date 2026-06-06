package technischools.projekt4.exception;

public class PostNotFound extends RuntimeException {
    public PostNotFound(Long id) {
        super("Post with id " + id.toString() + " not found");
    }
}
