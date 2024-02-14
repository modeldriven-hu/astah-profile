package hu.modeldriven.core.uml;

public enum UMLMetaClass {
    CLASS("Class"), PROPERTY("Property"), UNKNOWN("Unknown");

    private final String label;

    private UMLMetaClass(String label){
        this.label = label;
    }

    public String label() {
        return label;
    }
}
