package hu.modeldriven.astah.profile.ui.components.tree.wrapper;

import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.core.uml.*;

import java.util.List;

public class UMLStereotypeWrapper implements UMLStereotype {

    private final StereotypeTreeNode treeNode;
    private final UMLStereotype stereotype;

    public UMLStereotypeWrapper(StereotypeTreeNode treeNode, UMLStereotype stereotype){
        this.treeNode = treeNode;
        this.stereotype = stereotype;
    }

    @Override
    public String id() {
        return stereotype.id();
    }

    @Override
    public String name() {
        return stereotype.name();
    }

    @Override
    public void modifyName(String name) {
        stereotype.modifyName(name);
        treeNode.notifyChanged();
    }

    @Override
    public UMLMetaClass metaClass() {
        return stereotype.metaClass();
    }

    @Override
    public void modifyMetaClass(UMLMetaClass metaClass) {
        stereotype.modifyMetaClass(metaClass);
        treeNode.notifyChanged();
    }

    @Override
    public UMLProperty createChildProperty(String name, UMLPropertyType type) {
        UMLProperty property = stereotype.createChildProperty(name, type);
        treeNode.notifyChanged();
        return property;
    }

    @Override
    public void removeProperty(UMLProperty attribute) {
        stereotype.removeProperty(attribute);
        treeNode.notifyChanged();
    }

    @Override
    public List<UMLProperty> properties() {
        return stereotype.properties();
    }
}
