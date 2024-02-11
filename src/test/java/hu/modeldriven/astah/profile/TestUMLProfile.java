package hu.modeldriven.astah.profile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Test;

import java.util.Map;

/**
 * https://www.javatips.net/api/org.nabucco.framework.mda-master/org.nabucco.framework.mda.model.uml/src/main/org/nabucco/framework/mda/model/uml/UmlModelLoader.java
 * https://www.eclipse.org/forums/index.php/t/151604/
 * https://wiki.eclipse.org/MDT/UML2/FAQ#What.27s_required_to_load_a_UML_.28.uml.29_resource_from_a_standalone_application.3F.
 * https://github.com/MDE4CPP/MDE4CPP/blob/24e8ef69956d2a3c6b04dca3a61a97223f6aa959/generator/UML4CPP/UML4CPP.generator/src/UML4CPP/generator/main/Generate.java#L433
 * https://github.com/PasternakMichal/SimGen/blob/7276a61f301ee21daa5b88ddb72e068e12c56e7d/SimGen%20DSL/cs.queensu.ca.Unity/bin/cs/queensu/ca/generator/UMLRTLibraryGenerator.xtend#L71
 *
 */
public class TestUMLProfile {

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
        Profile profile = createProfile("test", "http://localhost");
        PrimitiveType booleanPrimitiveType = importPrimitiveType(profile, "Boolean");

        System.out.println(profile);
        System.out.println(booleanPrimitiveType);
    }

}
