package hu.modeldriven.astah.profile.ui.components.tree.wrapper;

import hu.modeldriven.astah.profile.ui.components.tree.PropertyTreeNode;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;

public class UMLPropertyWrapper implements UMLProperty {

    private final PropertyTreeNode treeNode;
    private final UMLProperty property;

    public UMLPropertyWrapper(PropertyTreeNode treeNode, UMLProperty property) {
        this.treeNode = treeNode;
        this.property = property;
    }

    @Override
    public String id() {
        return this.property.id();
    }

    @Override
    public String name() {
        return this.property.name();
    }

    @Override
    public void modifyName(String name) {
        this.property.modifyName(name);
        treeNode.notifyChanged();
    }

    @Override
    public UMLPropertyType type() {
        return this.property.type();
    }

    @Override
    public void modifyType(UMLPropertyType type) {
        this.property.modifyType(type);
        treeNode.notifyChanged();
    }
}
