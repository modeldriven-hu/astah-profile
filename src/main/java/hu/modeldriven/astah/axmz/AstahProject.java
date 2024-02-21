package hu.modeldriven.astah.axmz;

import hu.modeldriven.astah.axmz.impl.AxmzFileProfileSection;
import hu.modeldriven.astah.axmz.impl.AxmzFileProfilesSection;
import hu.modeldriven.astah.axmz.impl.ProfileUpgradePlan;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;

import java.io.File;
import java.util.List;

public class AstahProject {

    private final List<AxmzFileProfileSection> profileSections;

    public AstahProject(List<AxmzFileProfileSection> profileSections) {
        this.profileSections = profileSections;
    }

    public List<AxmzFileProfileSection> profiles(){
        return profileSections;
    }

    public UpgradePlan upgradeProfile(UMLProfile newProfile) throws UpgradeFailedException {

        AxmzFileProfileSection section = profileSections.stream()
                .filter(p -> p.umlProfile().name().equals(newProfile.name()))
                .findFirst()
                .orElseThrow(() ->
                        new UpgradeFailedException("No profile with name " + newProfile.name() + " in the model!"));

        UMLProfileDifference difference = section.umlProfile().difference(newProfile);

        return new ProfileUpgradePlan(section, difference);
    }

}
