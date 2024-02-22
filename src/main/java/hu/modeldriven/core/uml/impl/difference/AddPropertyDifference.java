package hu.modeldriven.core.uml.impl.difference;

import hu.modeldriven.core.uml.*;

public class AddPropertyDifference implements ProfileDifference {

    private final String stereotypeName;
    private final String propertyName;
    private final UMLPropertyType type;

    public AddPropertyDifference(UMLStereotype stereotype, UMLProperty property) {
        this.stereotypeName = stereotype.name();
        this.propertyName = property.name();
        this.type = property.type();
    }

    @Override
    public String description() {
        return "Add a new property " + propertyName + ":" + type.label() + " to stereotype " + stereotypeName;
    }

    @Override
    public void apply(UMLProfile profile) throws DifferenceNotApplicableException {
        UMLStereotype stereotype = profile.stereotypes().stream()
                .filter(s -> s.name().equals(stereotypeName))
                .findFirst()
                .orElseThrow(() -> new DifferenceNotApplicableException(
                        "Stereotype " + stereotypeName + " not found!"));

        stereotype.createChildProperty(propertyName, type);
    }
}
