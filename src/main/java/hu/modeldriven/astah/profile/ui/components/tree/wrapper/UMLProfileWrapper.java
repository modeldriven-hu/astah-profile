package hu.modeldriven.astah.profile.ui.components.tree.wrapper;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeNode;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLStereotype;

import java.io.File;
import java.util.List;

public class UMLProfileWrapper implements UMLProfile {

    private final ProfileTreeNode treeNode;
    private final UMLProfile profile;

    public UMLProfileWrapper(ProfileTreeNode treeNode, UMLProfile profile) {
        this.treeNode = treeNode;
        this.profile = profile;
    }

    @Override
    public String name() {
        return profile.name();
    }

    @Override
    public void modifyName(String name) {
        profile.modifyName(name);
        treeNode.notifyChanged();
    }

    @Override
    public String uri() {
        return profile.uri();
    }

    @Override
    public void modifyUri(String uri) {
        profile.modifyUri(uri);
        treeNode.notifyChanged();
    }

    @Override
    public UMLStereotype createChildStereotype(String name) {
        UMLStereotype stereotype = profile.createChildStereotype(name);
        treeNode.notifyStructureChanged();
        return stereotype;
    }

    @Override
    public void removeStereotype(UMLStereotype stereotype) {
        profile.removeStereotype(stereotype);
        treeNode.notifyStructureChanged();
    }

    @Override
    public List<UMLStereotype> stereotypes() {
        return profile.stereotypes();
    }

    @Override
    public void save(File file) {
        profile.save(file);
    }
}
