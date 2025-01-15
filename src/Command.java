import java.util.Arrays;
import java.util.Optional;

public enum Command {
    ADD,
    LIST;

    public static Optional<Command> fromString(String str) {
        return switch(str.toUpperCase()) {
            case "ADD" -> Optional.of(Command.ADD);
            case "LIST" -> Optional.of(Command.LIST);
            default -> Optional.empty();
        };
    }

    public static Optional<Command> getCommand(String[] args) {
        return Arrays.stream(args)
            .map(Command::fromString)
            .filter(Optional::isPresent)
            .findFirst()
            .orElse(Optional.empty());
    }
}
