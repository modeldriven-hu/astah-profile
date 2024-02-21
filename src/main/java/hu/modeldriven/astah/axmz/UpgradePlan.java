package hu.modeldriven.astah.axmz;

import java.util.List;

public interface UpgradePlan {

    public List<UpgradePlanStep> steps();

    public void execute();

}
