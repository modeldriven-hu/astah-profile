package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.core.eventbus.Event;

public class RemoveStereotypeRequestedEvent implements Event {

    private final StereotypeTreeNode treeNode;

    public RemoveStereotypeRequestedEvent(StereotypeTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public StereotypeTreeNode treeNode() {
        return treeNode;
    }
}
