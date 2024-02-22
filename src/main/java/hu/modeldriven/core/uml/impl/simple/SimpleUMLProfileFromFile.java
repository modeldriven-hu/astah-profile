package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.eclipse.EclipseRepresentation;
import hu.modeldriven.core.uml.impl.generic.MetaClassInProfile;
import hu.modeldriven.core.uml.impl.generic.PrimitiveTypesInProfile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import java.io.File;

public class SimpleUMLProfileFromFile {

    private final File file;
    private final EclipseRepresentation eclipseRepresentation;

    public SimpleUMLProfileFromFile(File file) {
        this.file = file;
        this.eclipseRepresentation = new EclipseRepresentation();
    }

    public UMLProfile umlProfile() throws ProfileCreationFailedException {
        try {
            Resource resource = eclipseRepresentation.resourceSet().getResource(URI.createFileURI(file.getAbsolutePath()), true);
            org.eclipse.uml2.uml.Package rootPackage = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);

            if (rootPackage instanceof Profile) {
                return umlProfile((Profile) rootPackage);
            }

            throw new WrappedException(new IllegalArgumentException("File contains a package, but it is not a profile!"));

        } catch (WrappedException e) {
            throw new ProfileCreationFailedException(e);
        }
    }

    private UMLProfile umlProfile(Profile profile) {
        PrimitiveTypesInProfile primitiveTypes = new PrimitiveTypesInProfile(profile, eclipseRepresentation);
        MetaClassInProfile metaClassInProfile = new MetaClassInProfile(profile, eclipseRepresentation);

        SimpleUMLProfile umlProfile = new SimpleUMLProfile(profile.getName(), profile.getURI());

        for (Stereotype stereotype : profile.getOwnedStereotypes()) {

            UMLStereotype umlStereotype = umlProfile.createChildStereotype(stereotype.getName());
            umlStereotype.modifyMetaClass(metaClassInProfile.metaClass(stereotype));

            for (Property property : stereotype.getOwnedAttributes()) {
                if (isNotInternalProperty(property)) {
                    umlStereotype.createChildProperty(property.getName(), primitiveTypes.propertyType(property.getType()));
                }
            }
        }

        return umlProfile;
    }

    private boolean isNotInternalProperty(Property property) {
        return !property.getName().startsWith("base_");
    }

}
