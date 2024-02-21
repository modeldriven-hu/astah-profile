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

    private final ZipFile modelFile;

    public AxmzFile(File file) {
        this.modelFile = new ZipFile(file);
    }

    public AstahProject project() throws AstahProjectImportFailedException {
        List<AxmzFileProfileSection> profiles = new ArrayList<>();

        UMLModel model = new EclipseUMLModel();

        List<AxmzFileSection> fileSections = Collections.singletonList(new AxmzFileProfilesSection(modelFile, model, profiles));

        loadSections(modelFile.uri(), fileSections);

        return new AstahProject(profiles);
    }

    private void loadSections(URI uri, List<AxmzFileSection> fileSections) throws AstahProjectImportFailedException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                        for (AxmzFileSection fileSection : fileSections) {
                            if (fileSection.appliesTo(file)) {
                                fileSection.process(file);
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
