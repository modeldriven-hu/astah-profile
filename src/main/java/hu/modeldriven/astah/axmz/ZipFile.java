package hu.modeldriven.astah.axmz;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

public class ZipFile {

    private final File file;

    public ZipFile(File file) {
        this.file = file;
    }

    public File file() {
        return this.file;
    }

    public URI uri() {
        return URI.create("jar:file:" + file.getPath());
    }

    public void copyFile(File file, Path matchingPath, CopyDirection direction) throws IOException {
        try (FileSystem fileSystem = FileSystems.newFileSystem(uri(), new HashMap<>())) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                        try {

                            if (path.getFileName().toString().equals(matchingPath.getFileName().toString())) {

                                if (direction.equals(CopyDirection.FILE_FROM_ZIP)) {
                                    Files.copy(path, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                } else{
                                    Files.copy(file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
                                }

                                return FileVisitResult.TERMINATE;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        }
    }

     public enum CopyDirection {
        FILE_TO_ZIP, FILE_FROM_ZIP
    }

}
