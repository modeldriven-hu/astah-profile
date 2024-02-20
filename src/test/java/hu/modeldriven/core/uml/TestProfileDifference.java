package hu.modeldriven.core.uml;

import hu.modeldriven.core.uml.impl.eclipse.EclipseModel;
import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestProfileDifference {

    @Test
    public void testAddStereotype() throws IOException, ProfileCreationFailedException, DifferenceNotApplicableException {
        UMLModel eclipseModel = new EclipseModel();

        // Create a base profile and save it to a file

        UMLProfile originalProfile = eclipseModel.profile("name", "url");
        UMLStereotype stereotype = originalProfile.createChildStereotype("S1", UMLMetaClass.CLASS);
        UMLProperty property = stereotype.createChildProperty("P1", UMLPropertyType.STRING);

        File tempFile = File.createTempFile("originalProfile", "profile.uml");
        originalProfile.save(tempFile);

        // Create a copy of the base profile

        UMLModel simpleModel = new SimpleUMLModel();
        UMLProfile newProfile = simpleModel.profile("name", "url");
        UMLStereotype newStereotype = newProfile.createChildStereotype("S1", UMLMetaClass.CLASS);
        UMLProperty newProperty = newStereotype.createChildProperty("P1", UMLPropertyType.STRING);

        // Create an addition to the base profile

        UMLStereotype addedStereotype = newProfile.createChildStereotype("S2", UMLMetaClass.CLASS);

        // Load the saved profile to ensure that everything is in place

        UMLProfile loadedProfile = eclipseModel.profile(tempFile);

        // Create the difference between the loaded profile and the new Profile

        UMLProfileDifference difference = eclipseModel.difference(loadedProfile, newProfile);

        // Apply the difference to the loaded profile

        difference.apply(loadedProfile);

        // Save the modified loaded profile

        File modifiedFile = File.createTempFile("modifiedProfile", "profile.uml");
        loadedProfile.save(modifiedFile);
    }
}
