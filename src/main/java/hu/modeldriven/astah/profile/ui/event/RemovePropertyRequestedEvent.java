package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.astah.profile.ui.components.tree.PropertyTreeNode;
import hu.modeldriven.core.eventbus.Event;

public class RemovePropertyRequestedEvent implements Event {
    private final PropertyTreeNode treeNode;

    public RemovePropertyRequestedEvent(PropertyTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public PropertyTreeNode treeNode() {
        return treeNode;
    }
}
