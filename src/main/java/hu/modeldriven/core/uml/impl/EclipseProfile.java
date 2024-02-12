package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EclipseProfile implements UMLProfile {

    private final Profile profile;
    private final ResourceSet resourceSet;

    List<UMLStereotype> stereotypes = new ArrayList<>();

    public EclipseProfile(Profile profile, ResourceSet resourceSet) {
        this.profile = profile;
        this.resourceSet = resourceSet;
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
    public UMLStereotype stereotype(String name) {

        if (stereotypes.stream().anyMatch(s -> s.name().equals(name))) {
            throw new ModelElementCreationException("Stereotype with name " + name + " already exists!");
        }

        Stereotype stereotype = profile.createOwnedStereotype(name, false);

        return new EclipseStereotype(stereotype, resourceSet);
    }

    @Override
    public void addStereotype(UMLStereotype stereotype) {
        stereotypes.add(stereotype);
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        stereotypes.remove(stereotype);
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return Collections.unmodifiableList(stereotypes);
    }
}
