package hu.modeldriven.astah.profile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * https://www.javatips.net/api/org.nabucco.framework.mda-master/org.nabucco.framework.mda.model.uml/src/main/org/nabucco/framework/mda/model/uml/UmlModelLoader.java
 * https://www.eclipse.org/forums/index.php/t/151604/
 * https://wiki.eclipse.org/MDT/UML2/FAQ#What.27s_required_to_load_a_UML_.28.uml.29_resource_from_a_standalone_application.3F.
 * https://github.com/MDE4CPP/MDE4CPP/blob/24e8ef69956d2a3c6b04dca3a61a97223f6aa959/generator/UML4CPP/UML4CPP.generator/src/UML4CPP/generator/main/Generate.java#L433
 * https://github.com/PasternakMichal/SimGen/blob/7276a61f301ee21daa5b88ddb72e068e12c56e7d/SimGen%20DSL/cs.queensu.ca.Unity/bin/cs/queensu/ca/generator/UMLRTLibraryGenerator.xtend#L71
 *
 *
 * https://medium.com/identity-beyond-borders/building-osgi-bundles-using-maven-bundle-plugin-with-embedded-dependencies-to-be-used-in-the-wso2-3b84b6a40ebe
 */
public class TestUMLProfile {

    private static final ResourceSet RESOURCE_SET;

    static {
        // Create a resource-set to contain the resource(s) that we load and
        // save
        RESOURCE_SET = new ResourceSetImpl();

        // Initialize registrations of resource factories, library models,
        // profiles, Ecore metadata, and other dependencies required for
        // serializing and working with UML resources. This is only necessary in
        // applications that are not hosted in the Eclipse platform run-time, in
        // which case these registrations are discovered automatically from
        // Eclipse extension points.
        UMLResourcesUtil.init(RESOURCE_SET);
    }

    private Profile createProfile(String name, String nsURI){
        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        profile.setURI(nsURI);
        return profile;
    }

    protected PrimitiveType importPrimitiveType(org.eclipse.uml2.uml.Package package_, String name) {
        org.eclipse.uml2.uml.Package umlLibrary = load(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));

        PrimitiveType primitiveType = (PrimitiveType) umlLibrary.getOwnedType(name);

        package_.createElementImport(primitiveType);

        return primitiveType;
    }

    protected Stereotype createStereotype(Profile profile, String name, boolean isAbstract) {
        Stereotype stereotype = profile.createOwnedStereotype(name, isAbstract);
        return stereotype;
    }

    protected static Property createAttribute(org.eclipse.uml2.uml.Class class_, String name, Type type, int lowerBound, int upperBound, Object defaultValue) {
        Property attribute = class_.createOwnedAttribute(name, type, lowerBound, upperBound);

        if (defaultValue instanceof Boolean) {
            LiteralBoolean literal = (LiteralBoolean) attribute.createDefaultValue(null, null, UMLPackage.Literals.LITERAL_BOOLEAN);
            literal.setValue(((Boolean) defaultValue).booleanValue());
        } else if (defaultValue instanceof String) {
            if (type instanceof Enumeration) {
                InstanceValue value = (InstanceValue) attribute.createDefaultValue(null, null, UMLPackage.Literals.INSTANCE_VALUE);
                value.setInstance(((Enumeration) type).getOwnedLiteral((String) defaultValue));
            } else {
                LiteralString literal = (LiteralString) attribute.createDefaultValue(null, null, UMLPackage.Literals.LITERAL_STRING);
                literal.setValue((String) defaultValue);
            }
        }

        return attribute;
    }

    protected org.eclipse.uml2.uml.Class referenceMetaclass(Profile profile, String name) {
        Model umlMetamodel = (Model) load(URI.createURI(UMLResource.UML_METAMODEL_URI));
        org.eclipse.uml2.uml.Class metaclass = (org.eclipse.uml2.uml.Class) umlMetamodel.getOwnedType(name);
        profile.createMetaclassReference(metaclass);
        return metaclass;
    }

    protected void defineProfile(Profile profile) {
        profile.define();
    }

    protected void save(org.eclipse.uml2.uml.Package package_, URI uri) {
        // Create the resource to be saved and add the package to it
        Resource resource = RESOURCE_SET.createResource(uri);
        resource.getContents().add(package_);

        // And save.
        try {
            resource.save(null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected Extension createExtension(org.eclipse.uml2.uml.Class metaclass, Stereotype stereotype, boolean required) {
        Extension extension = stereotype.createExtension(metaclass, required);
        return extension;
    }

    protected ResourceSet createResourceSet() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);

        Map uriMap = resourceSet.getURIConverter().getURIMap();
        URI uri = URI.createURI("jar:file:/usr/lib/astah_sysml/lib/org.eclipse.uml2.uml.resources-5.5.0.v20210228-1829.jar!/");
        uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles").appendSegment(""));

        return resourceSet;
    }

    protected org.eclipse.uml2.uml.Package load(URI uri) {
        try {
            Resource resource = createResourceSet().getResource(uri, true);
            return (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            we.printStackTrace();
        }

        return null;
    }

    @Test
    public void testUMLProfile() {
        Profile ecoreProfile = createProfile("test", "http://localhost");
        PrimitiveType booleanPrimitiveType = importPrimitiveType(ecoreProfile, "Boolean");
        Stereotype eStructuralFeatureStereotype = createStereotype(ecoreProfile, "EStructuralFeature", true);
        Property isTransientProperty = createAttribute(eStructuralFeatureStereotype, "isTransient", booleanPrimitiveType, 0, 1, null);
        org.eclipse.uml2.uml.Class propertyMetaclass = referenceMetaclass(ecoreProfile, UMLPackage.Literals.PROPERTY.getName());
        createExtension(propertyMetaclass, eStructuralFeatureStereotype, false);
        defineProfile(ecoreProfile);

        save(ecoreProfile, URI.createFileURI("/home/zsolt/test").appendSegment("Ecore").appendFileExtension(UMLResource.PROFILE_FILE_EXTENSION));

        System.out.println(ecoreProfile);
        System.out.println(booleanPrimitiveType);
    }

}
