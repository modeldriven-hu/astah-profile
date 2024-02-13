package hu.modeldriven.core.uml.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import java.io.File;
import java.io.IOException;

public class EclipseRepresentation {

    private final ResourceSet resourceSet;

    public EclipseRepresentation() {
        this.resourceSet = createResourceSet();
    }

    private ResourceSet createResourceSet() {
        ResourceSet result = UMLResourcesUtil.init(new ResourceSetImpl());
        result.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        result.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
        return result;
    }

    public ResourceSet resourceSet() {
        return this.resourceSet;
    }

    public org.eclipse.uml2.uml.Package load(URI uri) {
        try {
            Resource resource = resourceSet().getResource(uri, true);
            return (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            we.printStackTrace();
        }
        return null;
    }

    public void saveProfile(Profile profile, File file) {
        profile.define();

        Resource resource = resourceSet().createResource(URI.createFileURI(file.getAbsolutePath()));
        resource.getContents().add(profile);

        try {
            resource.save(null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
