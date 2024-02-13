package hu.modeldriven.core.uml;

import hu.modeldriven.core.uml.impl.EclipseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestUMLModel {

    private UMLModel model;

    @Before
    public void before() {
        model = new EclipseModel();
    }

    @Test
    public void testSaveProfile() throws IOException {
        UMLProfile profile = model.createProfile("MyProfile", "https://www.modeldriven.hu/schemas/myprofile");

        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");

        stereotype1.createChildProperty("someString", UMLPropertyType.String);
        stereotype1.createChildProperty("someBoolean", UMLPropertyType.Boolean);

        UMLStereotype stereotype2 = profile.createChildStereotype("Stereotype2");
        stereotype2.createChildProperty("someInt", UMLPropertyType.Integer);
        stereotype2.createChildProperty("someReal", UMLPropertyType.Real);
        stereotype2.createChildProperty("someNatural", UMLPropertyType.UnlimitedNatural);

        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals("Stereotype1")));
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals("Stereotype2")));

        File temporaryFile = File.createTempFile("testProfile", "profile.uml");
        profile.save(temporaryFile);
    }

    @Test
    public void testChangeProfileNameAndUri() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        Assert.assertEquals("MyProfile", profile.name());
        profile.modifyName("MyProfile2");
        Assert.assertEquals("MyProfile2", profile.name());

        Assert.assertEquals("test", profile.uri());
        profile.modifyUri("test2");
        Assert.assertEquals("test2", profile.uri());
    }

    @Test
    public void testAddRemoveStereotype() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype.name())));

        profile.removeStereotype(stereotype);
        Assert.assertEquals(0, profile.stereotypes().size());
    }

    @Test
    public void testAddMultipleStereotypes() {
        UMLProfile profile = model.createProfile("MyProfile", "test");
        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");
        UMLStereotype stereotype2 = profile.createChildStereotype("Stereotype2");

        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype1.name())));
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype2.name())));
    }

    @Test
    public void testChangeStereotypeName() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");

        Assert.assertEquals("Stereotype1", stereotype.name());
        stereotype.modifyName("Stereotype2");
        Assert.assertEquals("Stereotype2", stereotype.name());
    }

//    @Test
//    public void testChangeStereotypeMetaclass() {
//        UMLProfile profile = model.createProfile("MyProfile", "test");
//
//        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");
//        Assert.assertEquals(UMLMetaClass.Class, stereotype1.metaClass());
//
//        stereotype1.modifyMetaClass(UMLMetaClass.Property);
//        Assert.assertEquals(UMLMetaClass.Property, stereotype1.metaClass());
//
//        stereotype1.modifyMetaClass(UMLMetaClass.Class);
//        Assert.assertEquals(UMLMetaClass.Class, stereotype1.metaClass());
//    }

    @Test
    public void testAddProperty() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");

        for (UMLPropertyType type : UMLPropertyType.values()){
            stereotype.createChildProperty("some" + type.name(), type);
        }

        Assert.assertEquals(UMLPropertyType.values().length, stereotype.properties().size());
    }


    @Test
    public void testRemoveAndReAddProperty() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.String);

        Assert.assertEquals(1, stereotype.properties().size());
        stereotype.removeProperty(property);
        Assert.assertEquals(0, stereotype.properties().size());

        UMLProperty newProperty = stereotype.createChildProperty("name", UMLPropertyType.Boolean);
        Assert.assertEquals(1, stereotype.properties().size());
        Assert.assertEquals("name", newProperty.name());
        Assert.assertEquals(UMLPropertyType.Boolean, newProperty.type());
    }

    @Test
    public void testChangePropertyName() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.String);

        Assert.assertEquals("name", property.name());
        property.modifyName("name2");
        Assert.assertEquals("name2", property.name());
    }


    @Test
    public void testChangePropertyType() {
        UMLProfile profile = model.createProfile("MyProfile", "test");

        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.String);

        Assert.assertEquals(UMLPropertyType.String, property.type());
        property.modifyType(UMLPropertyType.Boolean);
        Assert.assertEquals(UMLPropertyType.Boolean, property.type());
    }

}
