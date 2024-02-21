package hu.modeldriven.astah.axmz;

import hu.modeldriven.astah.axmz.impl.ProfileUpgradePlan;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AstahProject {

    private final List<UMLProfile> umlProfiles;

    public AstahProject(List<UMLProfile> umlProfiles) {
        this.umlProfiles = umlProfiles;
    }

    public UpgradePlan upgradeProfile(UMLProfile newProfile) throws UpgradeFailedException {

        UMLProfile umlProfile = umlProfiles.stream()
                            .filter(p -> p.name().equals(newProfile.name()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new UpgradeFailedException("No profile with name " + newProfile.name() + " in the model!"));



        System.out.println("Profiles loaded");
        return new ProfileUpgradePlan();
    }

}
