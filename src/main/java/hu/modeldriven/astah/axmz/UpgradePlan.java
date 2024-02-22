package hu.modeldriven.astah.axmz;

import java.io.File;
import java.util.List;

public interface UpgradePlan {

    List<UpgradePlanStep> steps();

    void execute(File resultFile) throws UpgradeFailedException;

}
