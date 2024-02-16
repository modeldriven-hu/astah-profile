package hu.modeldriven.core.uml.impl.eclipse;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class EclipseProfile implements UMLProfile {

    private final Profile profile;
    private final EclipseRepresentation eclipseRepresentation;
    private final PrimitiveTypesInProfile primitiveTypes;
    private final MetaClassInProfile metaClasses;

    public EclipseProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {
        this.profile = profile;
        this.eclipseRepresentation = eclipseRepresentation;
        this.primitiveTypes = new PrimitiveTypesInProfile(profile, eclipseRepresentation);
        this.metaClasses = new MetaClassInProfile(profile, eclipseRepresentation);
    }

    @Override
    public String name() {
        return profile.getName();
    }

    @Override
    public void modifyName(String name) {
        profile.setName(name);
    }

    @Override
    public String uri() {
        return profile.getURI();
    }

    @Override
    public void modifyUri(String uri) {
        profile.setURI(uri);
    }

    @Override
    public UMLStereotype createChildStereotype(String name) {
        Stereotype stereotype = profile.createOwnedStereotype(name, false);
        UMLStereotype umlStereotype = new EclipseStereotype(stereotype, primitiveTypes, metaClasses);
        umlStereotype.modifyMetaClass(UMLMetaClass.CLASS);
        return umlStereotype;
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        profile.getOwnedStereotypes().removeIf(s -> s.getName().equals(stereotype.name()));
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return profile.getOwnedStereotypes().stream()
                .map(stereotype -> new EclipseStereotype(stereotype, primitiveTypes, metaClasses))
                .collect(Collectors.toList());
    }

    @Override
    public void save(File file) {
        eclipseRepresentation.saveProfile(profile, file);
    }

}
