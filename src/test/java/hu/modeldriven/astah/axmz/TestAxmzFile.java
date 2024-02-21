package hu.modeldriven.astah.axmz;

import hu.modeldriven.core.uml.*;
import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class TestAxmzFile {

    @Test
    public void testLoadFile() throws Exception {
        URL resource = getClass().getClassLoader().getResource("ImportedStereotype.axmz");
        File file = Paths.get(resource.toURI()).toFile();

        AxmzFile axmzFile = new AxmzFile(file);
        AstahProject project = axmzFile.project();

        UMLProfile modifiedProfile = createUMLProfile(project);

        UpgradePlan plan = project.upgradeProfile(modifiedProfile);

        File resultFile = File.createTempFile("merged-", ".axmz");

        System.out.println("Result file will be: " + resultFile);

        plan.execute(resultFile);
    }

    private UMLProfile createUMLProfile(AstahProject project) throws IOException, ProfileCreationFailedException {

        File tempFile = File.createTempFile("copy-", "profile.uml");

        if (project.profiles() == null || project.profiles().isEmpty()) {
            throw new ProfileCreationFailedException("Empty profiles");
        }

        project.profiles().get(0).save(tempFile);

        UMLModel model = new SimpleUMLModel();
        UMLProfile copyProfile = model.profile(tempFile);

        UMLStereotype copyStereotype = copyProfile.stereotypes().get(0);
        UMLProperty newProperty1 = copyStereotype.createChildProperty("P1_1", UMLPropertyType.BOOLEAN);

        // Extend the base profile with a new stereotype and a new property

        UMLStereotype newStereotype = copyProfile.createChildStereotype("S2", UMLMetaClass.CLASS);
        UMLProperty newProperty2 = newStereotype.createChildProperty("P2", UMLPropertyType.INTEGER);

        return copyProfile;
    }

}
