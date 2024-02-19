package hu.modeldriven.core.uml.impl.difference;

import hu.modeldriven.core.uml.DifferenceNotApplicableException;
import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

public class AddStereotypeDifference implements ProfileDifference {

    private final String stereotypeName;
    private final UMLMetaClass metaClass;

    public AddStereotypeDifference(UMLStereotype stereotype) {
        this.stereotypeName = stereotype.name();
        this.metaClass = stereotype.metaClass();
    }

    @Override
    public String description() {
        return "Create a new stereotype " + stereotypeName + ":" + metaClass.label();
    }

    @Override
    public void apply(UMLProfile profile) throws DifferenceNotApplicableException {

        if (profile.stereotypes().stream().anyMatch(s -> s.name().equals(stereotypeName))) {
            throw new DifferenceNotApplicableException("Stereotype with name "
                    + stereotypeName + " already exists");
        }

        profile.createChildStereotype(stereotypeName, metaClass);
    }
}
