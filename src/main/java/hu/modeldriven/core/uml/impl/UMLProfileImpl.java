package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.Collections;
import java.util.List;

public class UMLProfileImpl implements UMLProfile {

    private String name;
    private final String uri;

    public UMLProfileImpl(String name, String uri){
        this.name = name;
        this.uri = uri;
    }

    @Override
    public void setName(String name) {
        // This is a mutable solution
        this.name = name;
    }

    @Override
    public UMLProfile name(String name){
        // This is an immutable solution
        return new UMLProfileImpl(name, this.uri);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return Collections.emptyList();
    }
}
