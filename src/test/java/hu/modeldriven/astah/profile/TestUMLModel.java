package hu.modeldriven.astah.profile;

import hu.modeldriven.core.uml.*;
import hu.modeldriven.core.uml.impl.EclipseModel;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestUMLModel {

    @Test
    public void testProfileCreation() throws IOException {
        UMLModel model = new EclipseModel();
        UMLProfile profile = model.createProfile("MyProfile","https://www.modeldriven.hu/schemas/myprofile");

        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");

        stereotype1.createChildProperty("someString", UMLPropertyType.String);
        stereotype1.createChildProperty("someBoolean", UMLPropertyType.Boolean);

        UMLStereotype stereotype2 = profile.createChildStereotype("Stereotype2");
        stereotype2.createChildProperty("someInt", UMLPropertyType.Integer);
        stereotype2.createChildProperty("someReal", UMLPropertyType.Real);
        stereotype2.createChildProperty("someNatural", UMLPropertyType.UnlimitedNatural);

        File temporaryFile = File.createTempFile("testProfile","profile.uml");
        profile.save(temporaryFile);
    }

}
