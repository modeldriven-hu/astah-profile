package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.core.eventbus.Event;

public class CreatePropertyRequestedEvent implements Event {

    private final StereotypeTreeNode treeNode;

    public CreatePropertyRequestedEvent(StereotypeTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public StereotypeTreeNode treeNode() {
        return treeNode;
    }
}
