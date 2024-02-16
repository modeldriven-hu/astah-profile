package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.astah.profile.ui.components.tree.wrapper.UMLPropertyWrapper;
import hu.modeldriven.core.uml.UMLProperty;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class PropertyTreeNode implements TreeNode {

    private final StereotypeTreeNode parent;

    private final DefaultTreeModel treeModel;
    private final UMLProperty property;

    public PropertyTreeNode(StereotypeTreeNode parent, DefaultTreeModel treeModel, UMLProperty property) {
        this.parent = parent;
        this.treeModel = treeModel;
        this.property = new UMLPropertyWrapper(this, property);
    }

    public UMLProperty property() {
        return this.property;
    }

    @Override
    public TreeNode getChildAt(int i) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode treeNode) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return null;
    }

    public void notifyChanged() {
        treeModel.nodeChanged(this);
    }

}
