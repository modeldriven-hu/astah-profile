package hu.modeldriven.core.uml.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import java.util.Map;

public class ResourceSetBuilder {

    // FIXME handle cases when running in an OSGI environment vs not running in one
    protected ResourceSet create() {
        ResourceSet resourceSet = UMLResourcesUtil.init(new ResourceSetImpl());

        resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);

        Map<URI, URI> uriMap = resourceSet.getURIConverter().getURIMap();
        URI uri = URI.createURI("jar:file:/usr/lib/astah_sysml/lib/org.eclipse.uml2.uml.resources-5.5.0.v20210228-1829.jar!/");
        uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels").appendSegment(""));
        uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles").appendSegment(""));

        return resourceSet;
    }

}
