import java.util.Optional;

public record Task(
    String name,
    Priority priority,
    Status status
) {
}
