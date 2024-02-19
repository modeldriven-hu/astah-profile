package hu.modeldriven.core.uml;

import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestUMLModel {

    private UMLModel model;
    private UMLProfile profile;

    @Before
    public void before() {
        this.model = new SimpleUMLModel();
        this.profile = model.profile("MyProfile", "https://www.modeldriven.hu/schemas/myprofile");
    }

    @Test
    public void testLoadProfile() throws URISyntaxException, ProfileCreationFailedException {

        URL resource = getClass().getClassLoader().getResource("test2.profile.uml");
        File file = Paths.get(resource.toURI()).toFile();

        UMLProfile loadedProfile = model.profile(file);

        Assert.assertEquals(2, loadedProfile.stereotypes().size());
    }

    @Test
    public void testSaveProfile() throws IOException {
        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");
        stereotype1.modifyMetaClass(UMLMetaClass.PROPERTY);
        stereotype1.createChildProperty("someString", UMLPropertyType.STRING);
        stereotype1.createChildProperty("someBoolean", UMLPropertyType.BOOLEAN);

        UMLStereotype stereotype2 = profile.createChildStereotype("Stereotype2");
        stereotype2.createChildProperty("someInt", UMLPropertyType.INTEGER);
        stereotype2.createChildProperty("someReal", UMLPropertyType.REAL);
        stereotype2.createChildProperty("someNatural", UMLPropertyType.UNLIMITED_NATURAL);

        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> "Stereotype1".equals(s.name())));
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> "Stereotype2".equals(s.name())));

        File temporaryFile = new File("/Users/zsolt/multiData.uml");//File.createTempFile("testProfile", "profile.uml");
        profile.save(temporaryFile);
    }

    @Test
    public void testChangeProfileName() {
        Assert.assertEquals("MyProfile", profile.name());
        profile.modifyName("MyProfile2");
        Assert.assertEquals("MyProfile2", profile.name());
    }

    @Test
    public void testChangeProfileUri() {
        Assert.assertEquals("https://www.modeldriven.hu/schemas/myprofile", profile.uri());
        profile.modifyUri("test2");
        Assert.assertEquals("test2", profile.uri());
    }

    @Test
    public void testAddRemoveStereotype() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype.name())));

        profile.removeStereotype(stereotype);
        Assert.assertEquals(0, profile.stereotypes().size());
    }

    @Test
    public void testAddMultipleStereotypes() {
        UMLStereotype stereotype1 = profile.createChildStereotype("Stereotype1");
        UMLStereotype stereotype2 = profile.createChildStereotype("Stereotype2");

        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype1.name())));
        Assert.assertTrue(profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotype2.name())));
    }

    @Test
    public void testChangeStereotypeName() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");

        Assert.assertEquals("Stereotype", stereotype.name());
        stereotype.modifyName("Stereotype2");
        Assert.assertEquals("Stereotype2", stereotype.name());
    }

    @Test
    public void testStereotypeDefaultMetaClass() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");
        Assert.assertEquals(UMLMetaClass.CLASS, stereotype.metaClass());
    }

    @Test
    public void testChangeToSameMetaClass() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");

        stereotype.modifyMetaClass(UMLMetaClass.CLASS);
        Assert.assertEquals(UMLMetaClass.CLASS, stereotype.metaClass());

        stereotype.modifyMetaClass(UMLMetaClass.CLASS);
        Assert.assertEquals(UMLMetaClass.CLASS, stereotype.metaClass());
    }

    @Test
    public void testChangeToDifferentMetaClass() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype1");

        stereotype.modifyMetaClass(UMLMetaClass.CLASS);
        Assert.assertEquals(UMLMetaClass.CLASS, stereotype.metaClass());

        stereotype.modifyMetaClass(UMLMetaClass.PROPERTY);
        Assert.assertEquals(UMLMetaClass.PROPERTY, stereotype.metaClass());
    }

    @Test
    public void testAddProperty() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");

        for (UMLPropertyType type : UMLPropertyType.values()) {
            stereotype.createChildProperty("some" + type.name(), type);
        }

        Assert.assertEquals(UMLPropertyType.values().length, stereotype.properties().size());
    }


    @Test
    public void testRemoveAndReAddProperty() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.STRING);

        Assert.assertEquals(1, stereotype.properties().size());
        stereotype.removeProperty(property);
        Assert.assertEquals(0, stereotype.properties().size());

        UMLProperty newProperty = stereotype.createChildProperty("name", UMLPropertyType.BOOLEAN);
        Assert.assertEquals(1, stereotype.properties().size());
        Assert.assertEquals("name", newProperty.name());
        Assert.assertEquals(UMLPropertyType.BOOLEAN, newProperty.type());
    }


    @Test
    public void testRemoveMultipleProperty() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        UMLProperty property1 = stereotype.createChildProperty("name1", UMLPropertyType.STRING);
        UMLProperty property2 = stereotype.createChildProperty("name2", UMLPropertyType.STRING);

        stereotype.removeProperty(property2);
        stereotype.removeProperty(property1);

        Assert.assertEquals(0, stereotype.properties().size());
    }

    @Test
    public void testChangePropertyName() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.STRING);

        Assert.assertEquals("name", property.name());
        property.modifyName("name2");
        Assert.assertEquals("name2", property.name());
    }


    @Test
    public void testChangePropertyType() {
        UMLStereotype stereotype = profile.createChildStereotype("Stereotype");
        UMLProperty property = stereotype.createChildProperty("name", UMLPropertyType.STRING);

        Assert.assertEquals(UMLPropertyType.STRING, property.type());
        property.modifyType(UMLPropertyType.BOOLEAN);
        Assert.assertEquals(UMLPropertyType.BOOLEAN, property.type());
    }

}
