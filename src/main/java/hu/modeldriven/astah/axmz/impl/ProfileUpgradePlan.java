package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.UpgradeFailedException;
import hu.modeldriven.astah.axmz.UpgradePlan;
import hu.modeldriven.astah.axmz.UpgradePlanStep;
import hu.modeldriven.core.uml.DifferenceNotApplicableException;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ProfileUpgradePlan implements UpgradePlan {

    private final AxmzFileProfileSection section;
    private final UMLProfileDifference difference;

    public ProfileUpgradePlan(AxmzFileProfileSection section, UMLProfileDifference difference) {
        this.section = section;
        this.difference = difference;
    }

    @Override
    public List<UpgradePlanStep> steps() {
        return Collections.emptyList();
    }

    @Override
    public void execute() throws UpgradeFailedException {
        try {

            // Apply the difference on the UML profile

            UMLProfile upgradedProfile = difference.apply(section.umlProfile());

            // Create a temporary file

            File tempFile = File.createTempFile("updated-", ".profile.uml");
            tempFile.deleteOnExit();

            // Save profile into the temporary file

            upgradedProfile.save(tempFile);

            // Put the profile back into the original file
            // FIXME shall we create a copy of a model file, or are we brave enough to update the real file?

        } catch (DifferenceNotApplicableException | IOException e){
            throw new UpgradeFailedException(e);
        }
    }
}
