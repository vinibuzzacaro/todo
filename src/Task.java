import java.util.Optional;

public class Task {
    private String name;
    private Priority priority;
    private Status status;

    public Task(String name, Optional<Priority> priority) {
        this.name = name;
        this.priority = priority.orElse(Priority.NORMAL);
        this.status = Status.PENDING;
    }

    public String getName() {
        return this.name;
    }

    public Priority getPriority() {
        return this.priority;
    }
}
