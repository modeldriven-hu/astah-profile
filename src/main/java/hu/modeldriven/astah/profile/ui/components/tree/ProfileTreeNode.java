package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.astah.profile.ui.components.tree.wrapper.UMLProfileWrapper;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ProfileTreeNode implements TreeNode {

    private final DefaultTreeModel treeModel;
    private final UMLProfile profile;

    public ProfileTreeNode(DefaultTreeModel treeModel, UMLProfile profile){
        this.treeModel = treeModel;
        this.profile = new UMLProfileWrapper(this, profile);
    }

    public UMLProfile profile(){
        return this.profile;
    }

    @Override
    public TreeNode getChildAt(int i) {
        return new StereotypeTreeNode(this, treeModel, profile.stereotypes().get(i));
    }

    @Override
    public int getChildCount() {
        return this.profile.stereotypes().size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode treeNode) {

        List<UMLStereotype> stereotypeList = this.profile.stereotypes();

        if (stereotypeList.isEmpty()){
            return -1;
        }

        if (treeNode instanceof StereotypeTreeNode){
            StereotypeTreeNode stereotypeTreeNode = (StereotypeTreeNode) treeNode;
            UMLStereotype umlStereotype = stereotypeTreeNode.stereotype();

            for (int i = 0; i < stereotypeList.size(); i++) {
                if (umlStereotype.id().equals(stereotypeList.get(i).id())){
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
        List<StereotypeTreeNode> result = new ArrayList<>();

        for (UMLStereotype stereotype : this.profile.stereotypes()){
            result.add(new StereotypeTreeNode(this, treeModel, stereotype));
        }

        return Collections.enumeration(result);
    }

    public void notifyChanged() {
        treeModel.nodeChanged(this);
    }
}
