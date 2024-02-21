package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.UpgradeFailedException;
import hu.modeldriven.astah.axmz.UpgradePlan;
import hu.modeldriven.astah.axmz.UpgradePlanStep;
import hu.modeldriven.astah.axmz.ZipFile;
import hu.modeldriven.core.uml.DifferenceNotApplicableException;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProfileUpgradePlan implements UpgradePlan {

    private final AxmzFileProfileSection section;
    private final UMLProfileDifference difference;

    public ProfileUpgradePlan(AxmzFileProfileSection section, UMLProfileDifference difference) {
        this.section = section;
        this.difference = difference;
    }

    @Override
    public List<UpgradePlanStep> steps() {
        return Collections.emptyList();
    }

    @Override
    public void execute(File resultFile) throws UpgradeFailedException {
        try {

            // Apply the difference on the UML profile

            UMLProfile upgradedProfile = difference.apply(section.umlProfile());

            // Create a temporary file

            File tempFile = File.createTempFile("upgraded-", ".profile.uml");
            //tempFile.deleteOnExit();

            // Save profile into the temporary file

            upgradedProfile.save(tempFile);

            // Copy the original file to resultFile

            Files.copy(section.modelFile().file().toPath(), resultFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Open the result file

            try (FileSystem fileSystem = FileSystems.newFileSystem(new ZipFile(resultFile).uri(), new HashMap<>())) {

                for (Path rootDirectory : fileSystem.getRootDirectories()) {
                    Files.walkFileTree(rootDirectory, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                            try {

                                if (path.getFileName().toString().equals(section.profilePath().getFileName().toString())) {
                                    Files.copy(tempFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
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

        } catch (DifferenceNotApplicableException | IOException e) {
            throw new UpgradeFailedException(e);
        }
    }
}
