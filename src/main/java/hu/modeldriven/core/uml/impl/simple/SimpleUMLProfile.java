package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.difference.UMLProfileDifferenceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleUMLProfile implements UMLProfile {

    private final List<UMLStereotype> stereotypes;

    private String name;
    private String namespaceURI;

    public SimpleUMLProfile(String name, String namespaceURI) {
        this.name = name;
        this.namespaceURI = namespaceURI;
        this.stereotypes = new ArrayList<>();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void modifyName(String name) {
        this.name = name;
    }

    @Override
    public String uri() {
        return this.namespaceURI;
    }

    @Override
    public void modifyUri(String uri) {
        this.namespaceURI = uri;
    }

    @Override
    public UMLStereotype createChildStereotype(String name) {
        UMLStereotype stereotype = new SimpleUMLStereotype(this, name);
        this.stereotypes.add(stereotype);
        return stereotype;
    }

    @Override
    public UMLStereotype createChildStereotype(String name, UMLMetaClass metaClass) {
        UMLStereotype stereotype = createChildStereotype(name);
        stereotype.modifyMetaClass(metaClass);
        return stereotype;
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        this.stereotypes.removeIf(s -> s.id().equals(stereotype.id()));
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return this.stereotypes;
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
        new FileFromSimpleUMLProfile(this, file).save();
    }

    @Override
    public UMLProfileDifference difference(UMLProfile newProfile) {
        return new UMLProfileDifferenceImpl(this, newProfile);
    }
}
