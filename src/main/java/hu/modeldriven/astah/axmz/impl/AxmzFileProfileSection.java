package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.ZipFile;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

public class AxmzFileProfileSection {

    private final ZipFile modelFile;
    private final Path profilePath;
    private final UMLProfile umlProfile;

    public AxmzFileProfileSection(ZipFile modelFile, Path profilePath, UMLProfile umlProfile) {
        this.modelFile = modelFile;
        this.profilePath = profilePath;
        this.umlProfile = umlProfile;
    }

    public ZipFile modelFile() {
        return modelFile;
    }

    public Path profilePath() {
        return profilePath;
    }

    public UMLProfile umlProfile() {
        return umlProfile;
    }

    public void save(File targetFile) throws IOException {
        try (FileSystem fileSystem = FileSystems.newFileSystem(modelFile.uri(), new HashMap<>())) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        try {

                            if (file.toUri().equals(profilePath.toUri())) {
                                Files.copy(file, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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

}
