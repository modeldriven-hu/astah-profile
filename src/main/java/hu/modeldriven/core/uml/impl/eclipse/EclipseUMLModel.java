package hu.modeldriven.core.uml.impl.eclipse;

import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import java.io.File;

public class EclipseUMLModel implements UMLModel {

    private final EclipseRepresentation eclipseRepresentation;

    public EclipseUMLModel() {
        this.eclipseRepresentation = new EclipseRepresentation();
    }

    @Override
    public UMLProfile profile(File file) throws ProfileCreationFailedException {
        try {
            Resource resource = eclipseRepresentation.resourceSet().getResource(URI.createFileURI(file.getAbsolutePath()), true);
            org.eclipse.uml2.uml.Package rootPackage = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);

            if (rootPackage instanceof Profile) {
                return new EclipseUMLProfile((Profile) rootPackage, eclipseRepresentation);
            }

            throw new WrappedException(new IllegalArgumentException("File contains a package, but it is not a profile!"));

        } catch (WrappedException e) {
            throw new ProfileCreationFailedException(e);
        }
    }

    @Override
    public UMLProfile profile(String name, String namespaceURI) {

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        profile.setURI(namespaceURI);

        return new EclipseUMLProfile(profile, eclipseRepresentation);
    }

}
