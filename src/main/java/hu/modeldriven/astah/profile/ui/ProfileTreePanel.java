package hu.modeldriven.astah.profile.ui;

import hu.modeldriven.astah.profile.ui.components.table.FieldRow;
import hu.modeldriven.astah.profile.ui.components.table.ValueColumnCellEditor;
import hu.modeldriven.astah.profile.ui.components.table.ValueColumnCellRenderer;
import hu.modeldriven.astah.profile.ui.components.tree.*;
import hu.modeldriven.astah.profile.ui.event.*;
import hu.modeldriven.astah.profile.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.UMLModel;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class ProfileTreePanel extends AbstractProfileTreePanel {

    private static final String BUTTON_FOREGROUND = "Button.foreground";

    private final transient EventBus eventBus;

    private final transient UMLModel model;

    public ProfileTreePanel(EventBus eventBus, UMLModel model) {
        super();
        this.eventBus = eventBus;
        this.model = model;
        initUIComponents();
        initActions();
        initUseCases();
        initTree();
    }

    private void initUIComponents() {

        this.table.setDefaultEditor(FieldRow.class, new ValueColumnCellEditor());
        this.table.setDefaultRenderer(FieldRow.class, new ValueColumnCellRenderer());

        this.tree.setLargeModel(true);
        this.tree.setCellRenderer(new ProfileTreeCellRenderer());

        this.tree.addTreeSelectionListener(treeSelectionEvent -> {
            TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();

            if (node == null) {
                return;
            }

            if (node instanceof ProfileTreeNode) {
                eventBus.publish(new ProfileSelectedEvent(((ProfileTreeNode) node).profile()));
            }

            if (node instanceof StereotypeTreeNode) {
                eventBus.publish(new StereotypeSelectedEvent(((StereotypeTreeNode) node).stereotype()));
            }

            if (node instanceof PropertyTreeNode) {
                eventBus.publish(new PropertySelectedEvent(((PropertyTreeNode) node).property()));
            }

        });

        this.tree.addMouseListener(new ProfileTreeMouseAdapter(eventBus));

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

        updateButton.setIcon(FontIcon.of(
                MaterialDesign.MDI_UPDATE,
                16,
                UIManager.getColor(BUTTON_FOREGROUND)));

    }


    private void initActions() {
        newButton.addActionListener(actionEvent -> eventBus.publish(new NewProfileRequestedEvent()));
        openButton.addActionListener(actionEvent -> eventBus.publish(new LoadProfileRequestedEvent()));
        saveButton.addActionListener(actionEvent -> eventBus.publish(new SaveProfileRequestedEvent()));
        updateButton.addActionListener(actionEvent -> eventBus.publish(new UpdateModelRequestedEvent()));
    }

    private void initUseCases() {
        eventBus.subscribe(new InitTreeUseCase(tree));
        eventBus.subscribe(new LoadProfileUseCase(eventBus, this, model));
        eventBus.subscribe(new DisplayExceptionUseCase());
        eventBus.subscribe(new DisplayTableOnSelectionUseCase(table));
        eventBus.subscribe(new SaveProfileUseCase(eventBus, this));
        eventBus.subscribe(new CreateProfileUseCase(eventBus, model));
        eventBus.subscribe(new CreateStereotypeUseCase());
        eventBus.subscribe(new CreatePropertyUseCase());
        eventBus.subscribe(new RemoveStereotypeUseCase());
        eventBus.subscribe(new RemovePropertyUseCase());
        eventBus.subscribe(new UpdateModelUseCase(eventBus, this));
    }

    private void initTree() {
        eventBus.publish(new ProfileAvailableEvent(model.profile("Template", "https://www.example.com")));
    }

}
