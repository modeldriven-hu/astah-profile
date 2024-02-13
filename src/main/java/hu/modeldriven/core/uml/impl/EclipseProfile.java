package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EclipseProfile implements UMLProfile {

    private final Profile profile;
    private final EclipseRepresentation eclipseRepresentation;
    private final List<UMLStereotype> stereotypes;

    private final PrimitiveTypesInProfile primitiveTypes;

    public EclipseProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {
        this.profile = profile;
        this.eclipseRepresentation = eclipseRepresentation;
        this.stereotypes = new ArrayList<>();
        this.primitiveTypes = new PrimitiveTypesInProfile(profile, eclipseRepresentation);
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

        if (stereotypes.stream().anyMatch(s -> name.equals(s.name()))) {
            throw new ModelElementCreationException("Stereotype with name " + name + " already exists!");
        }

        Stereotype stereotype = profile.createOwnedStereotype(name, false);

        return new EclipseStereotype(stereotype, eclipseRepresentation, primitiveTypes);
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        stereotypes.remove(stereotype);
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return Collections.unmodifiableList(stereotypes);
    }

    @Override
    public void save(File file) {

        Model umlMetamodel = (Model) eclipseRepresentation.load(URI.createURI(UMLResource.UML_METAMODEL_URI));

        org.eclipse.uml2.uml.Class metaclass = (org.eclipse.uml2.uml.Class) umlMetamodel.getOwnedType(UMLPackage.Literals.PROPERTY.getName());
        profile.createMetaclassReference(metaclass);

        List<Stereotype> stereotypeList = new ArrayList<>();

        for (Stereotype stereotype : profile.getOwnedStereotypes()) {
            stereotypeList.add(stereotype);
        }

        stereotypeList.forEach(stereotype -> stereotype.createExtension(metaclass, false));

        profile.define();

        Resource resource = eclipseRepresentation.resourceSet().createResource(URI.createFileURI(file.getAbsolutePath()));
        resource.getContents().add(profile);

        // And save.
        try {
            resource.save(null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
