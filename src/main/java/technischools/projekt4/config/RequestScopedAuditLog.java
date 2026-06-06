package technischools.projekt4.config;

import java.time.LocalDateTime;

// request scoped
public class RequestScopedAuditLog {
    public void log(String action, String user) {
        System.out.println("[" + LocalDateTime.now() + "] " + user + ": " + action);
    }
}
