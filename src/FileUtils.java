import java.io.*;

public class FileUtils {
    public static boolean fileIsEmpty(String filePath) {
        try(FileReader fileReader = new FileReader(filePath)) {
            return fileReader.read() == -1;
        } catch (Exception e) {
            return true;
        }
    }

    public static void saveTask(Task task, String dataPath) {
        JSON json = JSON.fromObject(task);
        String contentToWrite = fileIsEmpty(dataPath) ? json.getContent() : String.format(", %s", json.getContent());
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataPath, true))) {
            bufferedWriter.write(contentToWrite);
        } catch (IOException e) {
            System.err.println("Failed to save the task!");
        }
    }
}
