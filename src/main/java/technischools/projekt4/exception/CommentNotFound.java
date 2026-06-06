package technischools.projekt4.exception;

public class CommentNotFound extends RuntimeException {
    public CommentNotFound(Long id) { super("Comment with id: " + id.toString() + " not found"); }
}
