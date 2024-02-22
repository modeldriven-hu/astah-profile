package hu.modeldriven.astah.axmz;

import hu.modeldriven.astah.axmz.impl.AxmzModelProfile;
import hu.modeldriven.core.uml.UMLProfile;

import java.util.List;

public interface AstahProject {
    List<AxmzModelProfile> profiles();

    UpgradePlan upgradeProfile(UMLProfile newProfile) throws UpgradeFailedException;
}
