package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeNode;
import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.astah.profile.ui.event.RemoveStereotypeRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class RemoveStereotypeUseCase implements EventHandler<RemoveStereotypeRequestedEvent> {

    @Override
    public void handleEvent(RemoveStereotypeRequestedEvent event) {

        StereotypeTreeNode treeNode = event.treeNode();

        if (treeNode.getParent() instanceof ProfileTreeNode) {
            ProfileTreeNode profileTreeNode = (ProfileTreeNode) treeNode.getParent();
            profileTreeNode.profile().removeStereotype(treeNode.stereotype());
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(RemoveStereotypeRequestedEvent.class);
    }
}
