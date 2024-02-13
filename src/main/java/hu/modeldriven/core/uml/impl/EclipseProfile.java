package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EclipseProfile implements UMLProfile {

    private final Profile profile;
    private final ResourceSet resourceSet;
    private final List<UMLStereotype> stereotypes;

    private final PrimitiveTypesInProfile primitiveTypes;

    public EclipseProfile(Profile profile, ResourceSet resourceSet) {
        this.profile = profile;
        this.resourceSet = resourceSet;
        this.stereotypes = new ArrayList<>();
        this.primitiveTypes = new PrimitiveTypesInProfile(profile, resourceSet);
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

        if (stereotypes.stream().anyMatch(s -> name.equals(s.name()))) {
            throw new ModelElementCreationException("Stereotype with name " + name + " already exists!");
        }

        Stereotype stereotype = profile.createOwnedStereotype(name, false);

        return new EclipseStereotype(stereotype, resourceSet, primitiveTypes);
    }

    @Override
    public void addStereotype(UMLStereotype ... stereotypeList) {
        this.stereotypes.addAll(Arrays.asList(stereotypeList));
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
        profile.define();
        Resource resource = resourceSet.createResource(URI.createFileURI(file.getAbsolutePath()));
        resource.getContents().add(profile);

        // And save.
        try {
            resource.save(null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
