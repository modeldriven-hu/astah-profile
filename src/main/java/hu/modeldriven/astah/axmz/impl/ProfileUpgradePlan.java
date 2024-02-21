package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.UpgradePlan;
import hu.modeldriven.astah.axmz.UpgradePlanStep;

import java.util.Collections;
import java.util.List;

public class ProfileUpgradePlan implements UpgradePlan {
    @Override
    public List<UpgradePlanStep> steps() {
        return Collections.emptyList();
    }

    @Override
    public void execute() {
        // do nothing
    }
}
