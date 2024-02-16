package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.astah.profile.ui.event.CreatePropertyRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.CreateStereotypeRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.RemovePropertyRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.RemoveStereotypeRequestedEvent;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileTreeMouseAdapter extends MouseAdapter {

    private final EventBus eventBus;

    public ProfileTreeMouseAdapter(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            handleEvent(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            handleEvent(e);
        }
    }

    private void handleEvent(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        JTree tree = (JTree) e.getSource();
        TreePath path = tree.getPathForLocation(x, y);

        if (path == null) {
            return;
        }

        TreeNode rightClickedNode = (TreeNode) path.getLastPathComponent();

        TreePath[] selectionPaths = tree.getSelectionPaths();

        boolean isSelected = false;

        if (selectionPaths != null) {
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.equals(path)) {
                    isSelected = true;
                    break;
                }
            }
        }

        if (!isSelected) {
            tree.setSelectionPath(path);
        }

        JPopupMenu menu = new JPopupMenu();

        if (rightClickedNode instanceof ProfileTreeNode) {
            createProfileMenu(menu, (ProfileTreeNode) rightClickedNode);
        } else if (rightClickedNode instanceof StereotypeTreeNode) {
            createStereotypeMenu(menu, (StereotypeTreeNode) rightClickedNode);
        } else if (rightClickedNode instanceof PropertyTreeNode) {
            createPropertyMenu(menu, (PropertyTreeNode) rightClickedNode);
        } else {
            return;
        }

        menu.show(tree, x, y);
    }

    private void createProfileMenu(JPopupMenu menu, ProfileTreeNode treeNode) {
        JMenuItem addStereotypeMenuItem = new JMenuItem("Add Stereotype");
        addStereotypeMenuItem.addActionListener(event -> eventBus.publish(new CreateStereotypeRequestedEvent(treeNode)));
        menu.add(addStereotypeMenuItem);
    }

    private void createStereotypeMenu(JPopupMenu menu, StereotypeTreeNode treeNode) {
        JMenuItem addPropertyMenuItem = new JMenuItem("Add Property");
        addPropertyMenuItem.addActionListener(event -> eventBus.publish(new CreatePropertyRequestedEvent(treeNode)));
        menu.add(addPropertyMenuItem);

        JMenuItem removeStereotypeMenuItem = new JMenuItem("Remove Stereotype");
        removeStereotypeMenuItem.addActionListener(event -> eventBus.publish(new RemoveStereotypeRequestedEvent(treeNode)));
        menu.add(removeStereotypeMenuItem);
    }

    private void createPropertyMenu(JPopupMenu menu, PropertyTreeNode treeNode) {
        JMenuItem removePropertyMenuItem = new JMenuItem("Remove Property");
        removePropertyMenuItem.addActionListener(event -> eventBus.publish(new RemovePropertyRequestedEvent(treeNode)));
        menu.add(removePropertyMenuItem);
    }
}

