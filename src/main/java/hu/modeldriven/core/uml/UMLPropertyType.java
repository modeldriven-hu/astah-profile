package hu.modeldriven.core.uml;

import java.util.Arrays;

public enum UMLPropertyType {
    STRING("String"),
    INTEGER("Integer"),
    BOOLEAN("Boolean"),
    UNLIMITED_NATURAL("Unlimited Natural"),
    REAL("Real");

    private final String label;

    UMLPropertyType(String label) {
        this.label = label;
    }

    public static UMLPropertyType propertyType(String label) {
        return Arrays.stream(UMLPropertyType.values())
                .filter(propertyType -> propertyType.label().equals(label))
                .findFirst()
                .orElse(null);
    }

    public String label() {
        return label;
    }
}
