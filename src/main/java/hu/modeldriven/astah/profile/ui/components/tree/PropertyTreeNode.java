package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.core.uml.UMLProperty;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class PropertyTreeNode implements TreeNode {

    private final StereotypeTreeNode parent;
    private final UMLProperty property;

    public PropertyTreeNode(StereotypeTreeNode parent, UMLProperty property) {
        this.parent = parent;
        this.property = property;
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

}
