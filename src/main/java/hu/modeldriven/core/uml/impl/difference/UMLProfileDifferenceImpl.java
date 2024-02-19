package hu.modeldriven.core.uml.impl.difference;

import hu.modeldriven.core.uml.*;

import java.util.ArrayList;
import java.util.List;

public class UMLProfileDifferenceImpl implements UMLProfileDifference {

    private final UMLProfile originalProfile;
    private final UMLProfile newProfile;

    public UMLProfileDifferenceImpl(UMLProfile originalProfile, UMLProfile newProfile) {
        this.originalProfile = originalProfile;
        this.newProfile = newProfile;
    }

    private List<ProfileDifference> differences() {

        List<ProfileDifference> differences = new ArrayList<>();

        for (UMLStereotype newStereotype : newProfile.stereotypes()) {
            differences.addAll(stereotypeAdditions(originalProfile, newStereotype));

            for (UMLStereotype originalStereotype : originalProfile.stereotypes()) {
                differences.addAll(propertyAdditions(originalStereotype, newStereotype));
            }

        }

        return differences;
    }

    private List<ProfileDifference> propertyAdditions(UMLStereotype originalStereotype, UMLStereotype newStereotype) {

        List<ProfileDifference> result = new ArrayList<>();

        if (originalStereotype.name().equals(newStereotype.name())) {
            for (UMLProperty currentProperty : newStereotype.properties()) {
                if (!originalStereotype.contains(currentProperty)) {
                    result.add(new AddPropertyDifference(newStereotype, currentProperty));
                }

            }
        }

        return result;
    }

    private List<ProfileDifference> stereotypeAdditions(UMLProfile originalProfile, UMLStereotype newStereotype) {

        List<ProfileDifference> result = new ArrayList<>();

        if (!originalProfile.contains(newStereotype)) {
            result.add(new AddStereotypeDifference(newStereotype));
            for (UMLProperty property : newStereotype.properties()) {
                result.add(new AddPropertyDifference(newStereotype, property));
            }
        }

        return result;
    }

    @Override
    public void apply(UMLProfile profile) throws DifferenceNotApplicableException {
        for (ProfileDifference difference : differences()) {
            difference.apply(profile);
        }
    }
}
