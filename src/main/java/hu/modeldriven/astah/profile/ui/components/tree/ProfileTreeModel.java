package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.core.uml.UMLProfile;

import javax.swing.tree.DefaultTreeModel;

public class ProfileTreeModel extends DefaultTreeModel {

    public ProfileTreeModel(UMLProfile profile) {
        super(null);
        this.setRoot(new ProfileTreeNode(this, profile));
    }

}
