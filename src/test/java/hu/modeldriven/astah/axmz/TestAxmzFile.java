package hu.modeldriven.astah.axmz;

import hu.modeldriven.core.uml.UMLProfile;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestAxmzFile {

    @Test
    public void testLoadFile() throws URISyntaxException, UpgradeFailedException {
        URL resource = getClass().getClassLoader().getResource("ImportedStereotype.axmz");
        File file = Paths.get(resource.toURI()).toFile();
        AxmzFile axmzFile = new AxmzFile(file);

        UMLProfile profile = createUMLProfile();

        UpgradePlan plan = axmzFile.upgradeProfile(profile);

        for (UpgradePlanStep step : plan.steps()){
            System.out.println(step.asString());
        }

        plan.execute();
    }

    private UMLProfile createUMLProfile(){
        return null;
    }

}
