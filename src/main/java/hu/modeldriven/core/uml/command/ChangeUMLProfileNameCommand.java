package hu.modeldriven.core.uml.command;

import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.event.UMLProfileChangedEvent;
import org.eclipse.emf.common.command.Command;

public class ChangeUMLProfileNameCommand implements ProfileCommand {

    private final EventBus eventBus;
    private final UMLProfile profile;
    private final String name;

    public ChangeUMLProfileNameCommand(EventBus eventBus, UMLProfile profile, String name){
        this.eventBus = eventBus;
        this.profile = profile;
        this.name = name;
    }

    public void execute(){
    }

    void modifyImmutable(){
        UMLProfile newProfile = profile.name(name);
        eventBus.publish(new UMLProfileChangedEvent(newProfile));
    }

    void modifyMutable(){
        profile.setName(name);
        eventBus.publish(new UMLProfileChangedEvent(profile));
    }

}
