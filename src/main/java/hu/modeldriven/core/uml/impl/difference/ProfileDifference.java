package hu.modeldriven.core.uml.impl.difference;

import hu.modeldriven.core.uml.DifferenceNotApplicableException;
import hu.modeldriven.core.uml.UMLProfile;

public interface ProfileDifference {

    void apply(UMLProfile profile) throws DifferenceNotApplicableException;

    String description();

}
