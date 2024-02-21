package hu.modeldriven.astah.axmz;

import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.impl.eclipse.EclipseUMLModel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AxmzFile {

    private final File file;

    public AxmzFile(File file) {
        this.file = file;
    }

    public AstahProject project() throws AstahProjectImportFailedException{
        List<UMLProfile> umlProfiles = loadProfiles();
        return new AstahProject(umlProfiles);
    }

    private List<UMLProfile> loadProfiles() throws AstahProjectImportFailedException {

        List<UMLProfile> result = new ArrayList<>();

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        URI uri = URI.create("jar:file:" + file.getPath());

        UMLModel model = new EclipseUMLModel();

        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)  {

                        // FIXME handle the various file types in an OOP way, this is a quick and dirty
                        // solution, this will result also in a bigger refactoring later

                        if (file.toUri().getSchemeSpecificPart().endsWith(".profile.uml")){
                            try {

                                // Because the model cannot read a file directly from a zip file, therefore
                                // we need to copy it out into a temporary file, and use this file as an
                                // input to create a profile

                                File tempFile = File.createTempFile("temp-", ".profile.uml");
                                tempFile.deleteOnExit();

                                Files.copy(file, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                UMLProfile profile = model.profile(tempFile);
                                result.add(profile);
                            } catch (Exception e) {
                                e.printStackTrace();
                                // In any case of error we will log and continue
                            }
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        } catch (IOException e){
            throw new AstahProjectImportFailedException(e);
        }

        return result;
    }


}
