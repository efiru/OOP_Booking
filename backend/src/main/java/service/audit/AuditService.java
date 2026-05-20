package service.audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private static final String FILE = "audit.csv";
    private static AuditService instance;

    private AuditService() {}

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void log(String actionName) {
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write(actionName + "," + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea in fisierul de audit.", e);
        }
    }
}
