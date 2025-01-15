import java.util.Optional;

public enum Priority {
    LOW,
    NORMAL,
    HIGH;

    public static Optional<Priority> fromString(String str) {
        return switch(str.toUpperCase()) {
            case "L", "LOW" -> Optional.of(Priority.LOW);
            case "N", "NORMAL" -> Optional.of(Priority.NORMAL);
            case "H", "HIGH" -> Optional.of(Priority.HIGH);
            default -> Optional.empty();
        };
    }
}
