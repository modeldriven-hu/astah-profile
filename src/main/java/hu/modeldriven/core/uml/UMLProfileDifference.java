package hu.modeldriven.core.uml;

public interface UMLProfileDifference {

    UMLProfile apply(UMLProfile profile) throws DifferenceNotApplicableException;

}
