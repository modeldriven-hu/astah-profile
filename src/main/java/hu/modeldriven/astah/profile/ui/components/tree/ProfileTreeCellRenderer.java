package hu.modeldriven.astah.profile.ui.components.tree;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;

public class ProfileTreeCellRenderer extends DefaultTreeCellRenderer {

    private final Icon profileIcon;
    private final Icon stereotypeIcon;

    private final Icon propertyIcon;

    public ProfileTreeCellRenderer() {
        super();
        this.profileIcon = new ImageIcon(getClass().getResource("/icons/Profile.gif"));
        this.stereotypeIcon = new ImageIcon(getClass().getResource("/icons/Stereotype.gif"));
        this.propertyIcon = new ImageIcon(getClass().getResource("/icons/Property.gif"));
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
            this.setText(profile.name() + " " +profile.uri() );
            this.setIcon(profileIcon);
        }

        if (value instanceof StereotypeTreeNode){
            UMLStereotype stereotype = ((StereotypeTreeNode) value).stereotype();
            this.setText(stereotype.name() + " : " + stereotype.metaClass().label() );
            this.setIcon(stereotypeIcon);
        }

        if (value instanceof PropertyTreeNode){
            UMLProperty property = ((PropertyTreeNode) value).property();
            this.setText(property.name() + " : " + property.type().label() );
            this.setIcon(propertyIcon);
        }

        return this;
    }
}