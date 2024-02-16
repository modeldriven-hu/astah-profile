package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.impl.eclipse.EclipseProfile;
import hu.modeldriven.core.uml.impl.eclipse.EclipseRepresentation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;

import java.io.File;

public class SimpleUMLModel implements UMLModel {

    @Override
    public UMLProfile createProfile(File file) throws ProfileCreationFailedException {
        return new SimpleUMLProfileFromFile(file).umlProfile();
    }

    @Override
    public UMLProfile createProfile(String name, String namespaceURI) {
        return new SimpleUMLProfile(name, namespaceURI);
    }
}
