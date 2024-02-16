package hu.modeldriven.core.uml.simpleimpl;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleUMLProfile implements UMLProfile {

    private final List<UMLStereotype> stereotypes;

    private String name;
    private String namespaceURI;

    public SimpleUMLProfile(String name, String namespaceURI){
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
    public void removeStereotype(UMLStereotype stereotype) {
        this.stereotypes.removeIf(s -> s.id().equals(stereotype.id()));
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return this.stereotypes;
    }

    @Override
    public void save(File file) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
