package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.AstahProject;
import hu.modeldriven.astah.axmz.AstahProjectImportFailedException;
import hu.modeldriven.astah.axmz.AxmzFile;
import hu.modeldriven.core.zip.ZipFile;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.impl.eclipse.EclipseUMLModel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class AxmzFileImpl implements AxmzFile {

    private final ZipFile modelFile;

    public AxmzFileImpl(File file) {
        this.modelFile = new ZipFile(file);
    }

    @Override
    public AstahProject project() throws AstahProjectImportFailedException {
        List<AxmzModelProfile> profiles = new ArrayList<>();

        UMLModel model = new EclipseUMLModel();

        List<AxmzModelPart> definitions = Collections.singletonList(new AxmzModelProfilePart(modelFile, model, profiles));

        loadFileContent(modelFile.uri(), definitions);

        return new AstahProjectImpl(profiles);
    }

    private void loadFileContent(URI uri, List<AxmzModelPart> definitions) throws AstahProjectImportFailedException {

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                        for (AxmzModelPart definition : definitions) {
                            if (definition.appliesTo(file)) {
                                definition.process(file);
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
