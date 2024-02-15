package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.components.tree.PropertyTreeNode;
import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.astah.profile.ui.event.RemovePropertyRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class RemovePropertyUseCase implements EventHandler<RemovePropertyRequestedEvent> {

    @Override
    public void handleEvent(RemovePropertyRequestedEvent event) {

        PropertyTreeNode treeNode = event.treeNode();

        if (treeNode.getParent() instanceof StereotypeTreeNode){
            StereotypeTreeNode parent = (StereotypeTreeNode) treeNode.getParent();
            parent.stereotype().removeProperty(treeNode.property());
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(RemovePropertyRequestedEvent.class);
    }
}
