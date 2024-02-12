package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.Cardinality;
import hu.modeldriven.core.uml.UMLAttribute;
import hu.modeldriven.core.uml.UMLAttributeType;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Stereotype;

import java.util.List;

public class EclipseStereotype implements UMLStereotype {
    public EclipseStereotype(Stereotype stereotype, ResourceSet resourceSet) {
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void modifyName(String name) {

    }

    @Override
    public UMLAttribute attribute(String name, UMLAttributeType type, Cardinality cardinality) {
        return null;
    }

    @Override
    public void addAttribute(UMLAttribute attribute) {

    }

    @Override
    public void removeAttribute(UMLAttribute attribute) {

    }

    @Override
    public List<UMLAttribute> attributes() {
        return null;
    }
}
