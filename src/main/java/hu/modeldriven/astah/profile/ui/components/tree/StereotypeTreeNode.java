package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class StereotypeTreeNode implements TreeNode {

    private final ProfileTreeNode parent;
    private final UMLStereotype stereotype;

    public StereotypeTreeNode(ProfileTreeNode parent, UMLStereotype stereotype){
        this.parent = parent;
        this.stereotype = stereotype;
    }

    public UMLStereotype stereotype(){
        return this.stereotype;
    }

    @Override
    public TreeNode getChildAt(int i) {
        return new PropertyTreeNode(this, this.stereotype.properties().get(i));
    }

    @Override
    public int getChildCount() {
        return this.stereotype.properties().size();
    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public int getIndex(TreeNode treeNode) {
        List<UMLProperty> propertyList = this.stereotype.properties();

        if (propertyList.isEmpty()){
            return -1;
        }

        if (treeNode instanceof PropertyTreeNode){
            PropertyTreeNode propertyTreeNode = (PropertyTreeNode) treeNode;
            UMLProperty umlProperty = propertyTreeNode.property();

            for (int i = 0; i < propertyList.size(); i++) {
                if (umlProperty.equals(propertyList.get(i))){
                    return i;
                }
            }
        }

        return -1;

    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        List<PropertyTreeNode> result = new ArrayList<>();

        for (UMLProperty property : this.stereotype.properties()){
            result.add(new PropertyTreeNode(this, property));
        }

        return Collections.enumeration(result);
    }
}
