package hu.modeldriven.core.uml;

import java.util.Arrays;

public enum UMLMetaClass {
    CLASS("Class"), PROPERTY("Property"), UNKNOWN("Unknown");

    private final String label;

    private UMLMetaClass(String label){
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static UMLMetaClass metaClass(String label){
        return Arrays.stream(UMLMetaClass.values())
                .filter(meta -> meta.label().equals(label))
                .findFirst()
                .orElse(null);
    }

}
