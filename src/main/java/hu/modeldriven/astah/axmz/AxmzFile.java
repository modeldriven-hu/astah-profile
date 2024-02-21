package hu.modeldriven.astah.axmz;

import hu.modeldriven.astah.axmz.impl.ProfileUpgradePlan;
import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.impl.eclipse.EclipseModel;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AxmzFile {

    private final File file;

    public AxmzFile(File file) {
        this.file = file;
    }

    public UpgradePlan upgradeProfile(UMLProfile newProfile) throws UpgradeFailedException{

        try {

            List<UMLProfile> profiles = loadProfiles();

            System.out.println("Profiles loaded");

            //https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch12s03.html
            //https://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html

            return new ProfileUpgradePlan();

        } catch (IOException e){
            throw new UpgradeFailedException(e);
        }
    }

    private List<UMLProfile> loadProfiles() throws IOException {

        List<UMLProfile> result = new ArrayList<>();

        Map<String, String> env = new HashMap<>();
        env.put("create", "false");

        URI uri = URI.create("jar:file:" + file.getPath());

        UMLModel model = new EclipseModel();

        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)) {

            for (Path rootDirectory : fileSystem.getRootDirectories()) {
                Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)  {

                        if (file.toUri().getSchemeSpecificPart().endsWith(".profile.uml")){
                            try {
                                File tempFile = File.createTempFile("temp-", ".profile.uml");
                                tempFile.deleteOnExit();

                                Files.copy(file, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                                UMLProfile profile = model.profile(tempFile);
                                result.add(profile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        }

        return result;
    }

}
