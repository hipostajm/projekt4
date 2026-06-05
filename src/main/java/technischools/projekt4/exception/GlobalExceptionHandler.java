package technischools.projekt4.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFound e) {
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }
}
