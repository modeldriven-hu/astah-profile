package hu.modeldriven.astah.axmz;

import hu.modeldriven.astah.axmz.impl.AxmzFileProfileSection;
import hu.modeldriven.astah.axmz.impl.AxmzFileProfilesSection;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.impl.eclipse.EclipseUMLModel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class AxmzFile {

    private final File file;
    private final List<AxmzFileProfileSection> profiles;

    public AxmzFile(File file) {
        this.file = file;
        this.profiles = new ArrayList<>();
    }

    public AstahProject project() throws AstahProjectImportFailedException {
        loadParts();
        return new AstahProject(file, profiles);
    }

    // FIXME handle the various file types in an OOP way, this is a quick and dirty
    // solution, this will result also in a bigger refactoring later
    private void loadParts() throws AstahProjectImportFailedException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        URI uri = URI.create("jar:file:" + file.getPath());

        UMLModel model = new EclipseUMLModel();

        List<AxmzFileSection> fileParts = Collections.singletonList(new AxmzFileProfilesSection(uri, model, profiles));

        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                        for (AxmzFileSection filePart : fileParts) {
                            if (filePart.appliesTo(file)) {
                                filePart.process(file);
                            }
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        } catch (IOException e) {
            throw new AstahProjectImportFailedException(e);
        }
    }


}
