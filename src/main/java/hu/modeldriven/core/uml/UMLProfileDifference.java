package hu.modeldriven.core.uml;

public interface UMLProfileDifference {

    void apply(UMLProfile profile) throws DifferenceNotApplicableException;

}
