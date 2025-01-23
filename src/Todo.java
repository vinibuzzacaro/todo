import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class Todo {
    public static void saveRecord(Task task) throws IOException {
        final String dataPath = "src/data.txt";
        JSON json = JSON.fromObject(task);
        boolean fileIsEmpty = false;
        try(FileReader fr = new FileReader(dataPath)) {
            fileIsEmpty = fr.read() == -1;
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(dataPath, true))) {
            String contentToWrite = fileIsEmpty ? json.getContent() : String.format(", %s", json.getContent());
            bw.write(contentToWrite);
        }
    }

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
        try {
            saveRecord(task);
        } catch (Exception e) {
            System.err.println("Failed to save task to file!");
        }
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