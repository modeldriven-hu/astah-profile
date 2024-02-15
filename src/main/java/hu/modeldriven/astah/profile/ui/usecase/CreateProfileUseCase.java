package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.event.NewProfileRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CreateProfileUseCase implements EventHandler<NewProfileRequestedEvent> {

    private final EventBus eventBus;
    private final UMLModel model;

    public CreateProfileUseCase(EventBus eventBus, UMLModel model) {
        this.eventBus = eventBus;
        this.model = model;
    }

    @Override
    public void handleEvent(NewProfileRequestedEvent event) {
        UMLProfile profile = model.createProfile(
                "Profile_" + UUID.randomUUID(),
                "https://localhost");

        eventBus.publish(new ProfileAvailableEvent(profile));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(NewProfileRequestedEvent.class);
    }
}
