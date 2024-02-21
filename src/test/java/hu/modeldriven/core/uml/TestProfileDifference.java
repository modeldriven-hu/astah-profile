package hu.modeldriven.core.uml;

import hu.modeldriven.core.uml.impl.eclipse.EclipseUMLModel;
import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestProfileDifference {

    @Test
    public void testAddStereotype() throws IOException, ProfileCreationFailedException, DifferenceNotApplicableException {
        UMLModel eclipseModel = new EclipseUMLModel();

        // Create a base profile and save it as a temporary file

        UMLProfile originalProfile = eclipseModel.profile("name", "url");
        UMLStereotype stereotype = originalProfile.createChildStereotype("S1", UMLMetaClass.CLASS);
        UMLProperty property = stereotype.createChildProperty("P1", UMLPropertyType.STRING);

        File originalFile = File.createTempFile("originalProfile", "profile.uml");
        originalProfile.save(originalFile);

        // Load the profile as we would in the GUI app using SimpleUMLModel

        UMLModel simpleModel = new SimpleUMLModel();
        UMLProfile copyProfile = simpleModel.profile(originalFile);

        // Extend the base profile's existing stereotype with a new property

        UMLStereotype copyStereotype = copyProfile.stereotypes().get(0);
        UMLProperty newProperty1 = copyStereotype.createChildProperty("P1_1", UMLPropertyType.BOOLEAN);

        // Extend the base profile with a new stereotype and a new property

        UMLStereotype newStereotype = copyProfile.createChildStereotype("S2", UMLMetaClass.CLASS);
        UMLProperty newProperty2 = newStereotype.createChildProperty("P2", UMLPropertyType.INTEGER);
        
        // Load the saved profile to ensure that everything is in place
        // We need to do this because we want to retain the original identifiers which we cannot do with
        // the simpleModel implementation

        UMLProfile loadedProfile = eclipseModel.profile(originalFile);

        // Create the difference between the loaded profile and the modified profile

        UMLProfileDifference difference = loadedProfile.difference(copyProfile);

        // Apply the difference to the loaded profile

        difference.apply(loadedProfile);

        // Save the modified loaded profile

        File modifiedFile = File.createTempFile("modifiedProfile", "profile.uml");
        loadedProfile.save(modifiedFile);
    }
}
