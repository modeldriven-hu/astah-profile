package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;

public class ProfileTreeLabelRenderer extends DefaultTreeCellRenderer {

    public ProfileTreeLabelRenderer() {
        super();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof ProfileTreeNode){
            UMLProfile profile = ((ProfileTreeNode) value).profile();
            this.setText("" + profile.name() + ", " +profile.uri() );
        }

        if (value instanceof StereotypeTreeNode){
            UMLStereotype stereotype = ((StereotypeTreeNode) value).stereotype();
            this.setText("<S> " + stereotype.name() + " : " + stereotype.metaClass().label() );
        }

        if (value instanceof PropertyTreeNode){
            UMLProperty property = ((PropertyTreeNode) value).property();
            this.setText("<P> " + property.name() + " : " + property.type().label() );
        }

        return this;
    }
}