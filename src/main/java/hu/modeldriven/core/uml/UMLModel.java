package hu.modeldriven.core.uml;

import hu.modeldriven.core.uml.command.ProfileCommand;

public interface UMLModel {

    void execute(ProfileCommand command);

    UMLProfile profile();

}
