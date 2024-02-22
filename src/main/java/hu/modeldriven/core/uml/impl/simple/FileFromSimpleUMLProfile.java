package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.impl.eclipse.EclipseRepresentation;
import hu.modeldriven.core.uml.impl.generic.ProfileFromUMLProfile;
import org.eclipse.uml2.uml.Profile;

import java.io.File;

public class FileFromSimpleUMLProfile {

    private final UMLProfile umlProfile;
    private final File file;

    public FileFromSimpleUMLProfile(UMLProfile umlProfile, File file) {
        this.umlProfile = umlProfile;
        this.file = file;
    }

    public void save() {
        Profile profile = buildProfile(umlProfile);
        saveProfile(profile, file);
    }

    private Profile buildProfile(UMLProfile umlProfile) {
        return new ProfileFromUMLProfile(umlProfile).profile();
    }

    private void saveProfile(Profile profile, File file) {
        new EclipseRepresentation().saveProfile(profile, file);
    }


}
