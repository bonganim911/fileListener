import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.LinkOption.*;

public class FileWatcher {

    private Path path;

    public FileWatcher(Path path) {
        this.path = path;
    }

    public void listenToFolderChanges() throws IOException {

        try {
            Boolean isDirectory = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);

            if (!isDirectory) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Watching path: " + path);


        FileSystem fileSystem = path.getFileSystem();

        WatchService watchService = fileSystem.newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;

        while (true) {
            try {
                key = watchService.take();
                WatchEvent.Kind<?> kind;

                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        System.out.println("New path created: " + newPath);
                    }

                }

                if (!key.reset())
                    break;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
