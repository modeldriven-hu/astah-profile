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
        UMLProfile profile = model.profile("MyProfile","https://www.modeldriven.hu/schemas/myprofile");

        UMLStereotype stereotype1 = profile.stereotype("Stereotype1");

        UMLProperty someString = stereotype1.property("someString", UMLPropertyType.String);
        UMLProperty someBoolean = stereotype1.property("someBoolean", UMLPropertyType.Boolean);

        stereotype1.addProperty(someString, someBoolean);

        UMLStereotype stereotype2 = profile.stereotype("Stereotype2");
        UMLProperty someInt = stereotype2.property("someInt", UMLPropertyType.Integer);
        UMLProperty someReal = stereotype2.property("someReal", UMLPropertyType.Real);
        UMLProperty someNatural = stereotype2.property("someNatural", UMLPropertyType.UnlimitedNatural);

        stereotype2.addProperty(someInt, someReal, someNatural);

        profile.addStereotype(stereotype1, stereotype2);

        File temporaryFile = File.createTempFile("testProfile","profile.uml");
        profile.save(temporaryFile);
    }

}
