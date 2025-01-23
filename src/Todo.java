import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class Todo {
    public static void handleNewTask(Iterator<String> it) {
        if (!it.hasNext()) { return; }
        String name = it.next();
        if (it.hasNext() && !it.next().equalsIgnoreCase("-p")) {
            System.err.println("Invalid arguments");
            return;
        }
        String nextArg = it.hasNext() ? it.next() : null;
        Priority priority = nextArg == null || Priority.fromString(nextArg).isEmpty() ? Priority.NORMAL : Priority.fromString(nextArg).get();
        Task task = new Task(name, priority, Status.PENDING);
        System.out.printf("Created task \"%s\", Priority %s\n", task.name(), task.priority());
        JSON json = JSON.fromObject(task);
        System.out.println(json.getContent());
    }

    public static void handleListing(Iterator<String> it) {

    }

    public static void handleArgs(String[] args) {
        Iterator<String> it = Arrays.stream(args).iterator();
        if (!it.hasNext()) { return; }
        String commandStr = it.next();
        if (Command.fromString(commandStr).isEmpty()) { return; }
        Optional<Command> optCommand = Command.fromString(commandStr);
        if (optCommand.isEmpty()) { return; }
        switch (optCommand.get()) {
            case Command.ADD -> handleNewTask(it);
        }
    }

    public static void main(String[] args) {
        handleArgs(args);
    }
}