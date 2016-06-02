import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        FileWatcher fileWatcher = new FileWatcher(Paths.get("Insert a folder path"));
        fileWatcher.listenToFolderChanges();
    }
}
