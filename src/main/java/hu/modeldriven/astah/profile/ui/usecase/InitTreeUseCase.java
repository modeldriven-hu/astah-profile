package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeModel;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import java.util.Collections;
import java.util.List;

public class InitTreeUseCase implements EventHandler<ProfileAvailableEvent> {

    private final JTree tree;

    public InitTreeUseCase(JTree tree){
        this.tree = tree;
    }

    @Override
    public void handleEvent(ProfileAvailableEvent event) {
        ProfileTreeModel treeModel = new ProfileTreeModel(event.profile());
        tree.setModel(treeModel);
        treeModel.addTreeModelListener(new TreeModelListener(){

            @Override
            public void treeNodesChanged(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeNodesInserted(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent treeModelEvent) {
                // Do nothing
            }

            @Override
            public void treeStructureChanged(TreeModelEvent treeModelEvent) {
                tree.expandPath(treeModelEvent.getTreePath());
            }
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ProfileAvailableEvent.class);
    }
}
