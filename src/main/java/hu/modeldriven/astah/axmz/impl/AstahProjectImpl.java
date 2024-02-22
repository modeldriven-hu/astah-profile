package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.AstahProject;
import hu.modeldriven.astah.axmz.UpgradeFailedException;
import hu.modeldriven.astah.axmz.UpgradePlan;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;

import java.util.List;

public class AstahProjectImpl implements AstahProject {

    private final List<AxmzModelProfile> profileSections;

    public AstahProjectImpl(List<AxmzModelProfile> profileSections) {
        this.profileSections = profileSections;
    }

    @Override
    public List<AxmzModelProfile> profiles() {
        return profileSections;
    }

    @Override
    public UpgradePlan upgradeProfile(UMLProfile newProfile) throws UpgradeFailedException {

        AxmzModelProfile section = profileSections.stream()
                .filter(p -> p.umlProfile().name().equals(newProfile.name()))
                .findFirst()
                .orElseThrow(() ->
                        new UpgradeFailedException("No profile with name " + newProfile.name() + " in the model!"));

        UMLProfileDifference difference = section.umlProfile().difference(newProfile);

        return new ProfileUpgradePlan(section, difference);
    }

}
