package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLPropertyType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.util.EnumMap;
import java.util.Map;

public class PrimitiveTypesInProfile {

    private final Map<UMLPropertyType, PrimitiveType> primitiveTypeMap;

    public PrimitiveTypesInProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {
        ResourceSet resourceSet = eclipseRepresentation.resourceSet();

        primitiveTypeMap = new EnumMap<>(UMLPropertyType.class);
        primitiveTypeMap.put(UMLPropertyType.Boolean, importPrimitiveType(profile, loadLibrary(resourceSet), "Boolean"));
        primitiveTypeMap.put(UMLPropertyType.Real, importPrimitiveType(profile, loadLibrary(resourceSet), "Real"));
        primitiveTypeMap.put(UMLPropertyType.Integer, importPrimitiveType(profile, loadLibrary(resourceSet), "Integer"));
        primitiveTypeMap.put(UMLPropertyType.String, importPrimitiveType(profile, loadLibrary(resourceSet), "String"));
        primitiveTypeMap.put(UMLPropertyType.UnlimitedNatural, importPrimitiveType(profile, loadLibrary(resourceSet), "UnlimitedNatural"));
    }

    public PrimitiveType primitiveType(UMLPropertyType type) {
        return primitiveTypeMap.get(type);
    }

    public UMLPropertyType propertyType(Type type) {

        for (Map.Entry<UMLPropertyType, PrimitiveType> entry : primitiveTypeMap.entrySet()) {
            if (type.conformsTo(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    private org.eclipse.uml2.uml.Package loadLibrary(ResourceSet resourceSet) {
        return load(resourceSet, URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));
    }

    private PrimitiveType importPrimitiveType(org.eclipse.uml2.uml.Package currentProfile,
                                              org.eclipse.uml2.uml.Package umlLibrary,
                                              String name) {
        if (currentProfile == null) {
            throw new IllegalArgumentException("Current profile is null");
        }

        if (umlLibrary == null) {
            throw new IllegalArgumentException("UML library is null");
        }

        PrimitiveType primitiveType = (PrimitiveType) umlLibrary.getOwnedType(name);
        currentProfile.createElementImport(primitiveType);
        return primitiveType;
    }

    private org.eclipse.uml2.uml.Package load(ResourceSet resourceSet, URI uri) {
        try {
            Resource resource = resourceSet.getResource(uri, true);
            return (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            we.printStackTrace();
        }
        return null;
    }

}
