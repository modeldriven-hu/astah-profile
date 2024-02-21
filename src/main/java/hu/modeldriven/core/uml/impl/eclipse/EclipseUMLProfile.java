package hu.modeldriven.core.uml.impl.eclipse;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.difference.UMLProfileDifferenceImpl;
import hu.modeldriven.core.uml.impl.generic.MetaClassInProfile;
import hu.modeldriven.core.uml.impl.generic.PrimitiveTypesInProfile;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class EclipseUMLProfile implements UMLProfile {

    private final Profile profile;
    private final EclipseRepresentation eclipseRepresentation;
    private final PrimitiveTypesInProfile primitiveTypes;
    private final MetaClassInProfile metaClasses;

    public EclipseUMLProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {
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
        UMLStereotype umlStereotype = new EclipseUMLStereotype(stereotype, primitiveTypes, metaClasses);
        umlStereotype.modifyMetaClass(UMLMetaClass.CLASS);
        return umlStereotype;
    }

    @Override
    public UMLStereotype createChildStereotype(String name, UMLMetaClass metaClass) {
        UMLStereotype stereotype = createChildStereotype(name);
        stereotype.modifyMetaClass(metaClass);
        return stereotype;
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        profile.getOwnedStereotypes().removeIf(s -> s.getName().equals(stereotype.name()));
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return profile.getOwnedStereotypes().stream()
                .map(stereotype -> new EclipseUMLStereotype(stereotype, primitiveTypes, metaClasses))
                .collect(Collectors.toList());
    }

    @Override
    public boolean contains(UMLStereotype stereotype) {
        return stereotypes().stream()
                .anyMatch(s ->
                        s.name().equals(stereotype.name()) &&
                                s.metaClass().equals(stereotype.metaClass()));
    }

    @Override
    public void save(File file) {
        eclipseRepresentation.saveProfile(profile, file);
    }

    @Override
    public UMLProfileDifference difference(UMLProfile newProfile) {
        return new UMLProfileDifferenceImpl(this, newProfile);
    }

}
