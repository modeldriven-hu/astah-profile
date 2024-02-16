package hu.modeldriven.astah.profile;

import com.change_vision.jude.api.inf.ui.IPluginTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import hu.modeldriven.astah.profile.ui.ProfileTreePanel;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.impl.simple.SimpleUMLModel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;

public class ProfileBrowserView extends JPanel implements IPluginTabView {

    @Override
    public String getTitle() {
        return "UML Profiles";
    }

    @Override
    public String getDescription() {
        return "UML Profiles";
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object[] getSelectedModels() {
        return new Object[0];
    }

    @Override
    public void initTreeModel() {
        this.setLayout(new BorderLayout());
        EventBus eventBus = new EventBus();
        ProfileTreePanel treePanel = new ProfileTreePanel(eventBus, new SimpleUMLModel());
        this.add(treePanel, BorderLayout.CENTER);
    }

    @Override
    public void addSelectionListener(ISelectionListener iSelectionListener) {
        // Do nothing
    }

}

