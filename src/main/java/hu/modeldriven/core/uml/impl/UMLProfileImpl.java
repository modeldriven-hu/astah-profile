package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.List;

public class UMLProfileImpl implements UMLProfile {

    private String name;
    private String uri;

    private List<UMLStereotype> stereotypes;

    public UMLProfileImpl(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return stereotypes;
    }
}
