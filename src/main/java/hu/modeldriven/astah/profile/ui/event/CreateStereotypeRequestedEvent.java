package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeNode;
import hu.modeldriven.core.eventbus.Event;

public class CreateStereotypeRequestedEvent implements Event {

    private final ProfileTreeNode treeNode;

    public CreateStereotypeRequestedEvent(ProfileTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public ProfileTreeNode treeNode() {
        return treeNode;
    }
}
