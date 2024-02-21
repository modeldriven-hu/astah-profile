package hu.modeldriven.astah.axmz;

import java.util.List;

public interface UpgradePlan {

    List<UpgradePlanStep> steps();

    void execute() throws UpgradeFailedException;

}
