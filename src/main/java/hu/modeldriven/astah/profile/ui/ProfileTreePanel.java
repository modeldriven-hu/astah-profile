package hu.modeldriven.astah.profile.ui;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeLabelRenderer;
import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeNode;
import hu.modeldriven.astah.profile.ui.components.tree.PropertyTreeNode;
import hu.modeldriven.astah.profile.ui.components.tree.StereotypeTreeNode;
import hu.modeldriven.astah.profile.ui.event.*;
import hu.modeldriven.astah.profile.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.EclipseModel;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class ProfileTreePanel extends AbstractProfileTreePanel {

    private static final String BUTTON_FOREGROUND = "Button.foreground";

    private final EventBus eventBus;

    private final UMLModel model;

    public ProfileTreePanel(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.model = new EclipseModel();
        initUIComponents();
        initActions();
        initUseCases();
    }

    private void initUIComponents() {

        this.tree.setCellRenderer(new ProfileTreeLabelRenderer());

        this.tree.addTreeSelectionListener(treeSelectionEvent -> {
            TreeNode node = (TreeNode)tree.getLastSelectedPathComponent();

            if (node == null){
                return;
            }

            if (node instanceof ProfileTreeNode){
                eventBus.publish(new ProfileSelectedEvent(((ProfileTreeNode) node).profile()));
            }

            if (node instanceof StereotypeTreeNode){
                eventBus.publish(new StereotypeSelectedEvent(((StereotypeTreeNode) node).stereotype()));
            }

            if (node instanceof PropertyTreeNode){
                eventBus.publish(new PropertySelectedEvent(((PropertyTreeNode) node).property()));
            }

        });

        newButton.setIcon(FontIcon.of(
                MaterialDesign.MDI_FILE,
                16,
                UIManager.getColor(BUTTON_FOREGROUND)));

        openButton.setIcon(FontIcon.of(
                MaterialDesign.MDI_DESKTOP_MAC,
                16,
                UIManager.getColor(BUTTON_FOREGROUND)));

        saveButton.setIcon(FontIcon.of(
                MaterialDesign.MDI_CONTENT_SAVE,
                16,
                UIManager.getColor(BUTTON_FOREGROUND)));
    }


    private void initActions() {
        newButton.addActionListener(actionEvent -> eventBus.publish(new NewProfileRequestedEvent()));
        openButton.addActionListener(actionEvent -> eventBus.publish(new LoadProfileRequestedEvent()));
        saveButton.addActionListener(actionEvent -> eventBus.publish(new SaveProfileRequestedEvent()));
    }

    private void initUseCases() {
        eventBus.subscribe(new CreateNewProfileUseCase(eventBus, model));
        eventBus.subscribe(new InitTreeUseCase(tree));
        eventBus.subscribe(new LoadProfileUseCase(this,eventBus,model));
        eventBus.subscribe(new DisplayExceptionUseCase());
        eventBus.subscribe(new DisplayTableOnSelectionUseCase(table));
    }

}
