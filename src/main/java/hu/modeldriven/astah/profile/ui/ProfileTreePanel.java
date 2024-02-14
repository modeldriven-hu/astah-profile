package hu.modeldriven.astah.profile.ui;

import hu.modeldriven.astah.profile.ui.components.tree.ProfileTreeLabelRenderer;
import hu.modeldriven.astah.profile.ui.event.LoadProfileRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.NewProfileRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.SaveProfileRequestedEvent;
import hu.modeldriven.astah.profile.ui.usecase.CreateNewProfileUseCase;
import hu.modeldriven.astah.profile.ui.usecase.DisplayExceptionUseCase;
import hu.modeldriven.astah.profile.ui.usecase.InitTreeUseCase;
import hu.modeldriven.astah.profile.ui.usecase.LoadProfileUseCase;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.impl.EclipseModel;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;

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
    }

}
