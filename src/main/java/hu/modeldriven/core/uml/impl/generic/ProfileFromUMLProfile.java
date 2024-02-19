package hu.modeldriven.core.uml.impl.generic;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.eclipse.EclipseRepresentation;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;

public class ProfileFromUMLProfile {

    private final UMLProfile umlProfile;

    public ProfileFromUMLProfile(UMLProfile umlProfile) {
        this.umlProfile = umlProfile;
    }

    public Profile profile() {

        EclipseRepresentation eclipseRepresentation = new EclipseRepresentation();

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(umlProfile.name());
        profile.setURI(umlProfile.uri());

        MetaClassInProfile metaClassInProfile = new MetaClassInProfile(profile, eclipseRepresentation);
        PrimitiveTypesInProfile primitiveTypesInProfile = new PrimitiveTypesInProfile(profile, eclipseRepresentation);

        for (UMLStereotype umlStereotype : umlProfile.stereotypes()) {
            Stereotype stereotype = profile.createOwnedStereotype(umlStereotype.name(), false);
            stereotype.createExtension(metaClassInProfile.extensionType(umlStereotype.metaClass()), false);

            for (UMLProperty umlProperty : umlStereotype.properties()) {
                stereotype.createOwnedAttribute(
                        umlProperty.name(),
                        primitiveTypesInProfile.primitiveType(umlProperty.type()),
                        0,
                        1);
            }
        }

        return profile;
    }

}
