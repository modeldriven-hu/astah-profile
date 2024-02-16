package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLPropertyType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.util.EnumMap;
import java.util.Map;

public class PrimitiveTypesInProfile {

    private final Map<UMLPropertyType, PrimitiveType> primitiveTypeMap;
    private final EclipseRepresentation eclipseRepresentation;

    public PrimitiveTypesInProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {

        this.primitiveTypeMap = new EnumMap<>(UMLPropertyType.class);
        this.eclipseRepresentation = eclipseRepresentation;

        org.eclipse.uml2.uml.Package umlLibrary = loadLibrary();

        this.primitiveTypeMap.put(UMLPropertyType.BOOLEAN, importPrimitiveType(profile, umlLibrary, "Boolean"));
        this.primitiveTypeMap.put(UMLPropertyType.REAL, importPrimitiveType(profile, umlLibrary, "Real"));
        this.primitiveTypeMap.put(UMLPropertyType.INTEGER, importPrimitiveType(profile, umlLibrary, "Integer"));
        this.primitiveTypeMap.put(UMLPropertyType.STRING, importPrimitiveType(profile, umlLibrary, "String"));
        this.primitiveTypeMap.put(UMLPropertyType.UNLIMITED_NATURAL, importPrimitiveType(profile, umlLibrary, "UnlimitedNatural"));
    }

    public PrimitiveType primitiveType(UMLPropertyType type) {
        return primitiveTypeMap.get(type);
    }

    public UMLPropertyType propertyType(Type type) {

        if (type == null) {
            return null;
        }

        for (Map.Entry<UMLPropertyType, PrimitiveType> entry : primitiveTypeMap.entrySet()) {
            if (type.conformsTo(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
    }

    private org.eclipse.uml2.uml.Package loadLibrary() {
        return this.eclipseRepresentation.load(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));
    }

    private PrimitiveType importPrimitiveType(org.eclipse.uml2.uml.Package currentProfile,
                                              org.eclipse.uml2.uml.Package umlLibrary,
                                              String name) {
        PrimitiveType primitiveType = (PrimitiveType) umlLibrary.getOwnedType(name);
        currentProfile.createElementImport(primitiveType);
        return primitiveType;
    }


}
