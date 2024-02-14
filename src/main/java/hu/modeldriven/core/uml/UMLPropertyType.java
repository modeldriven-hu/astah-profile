package hu.modeldriven.core.uml;

public enum UMLPropertyType {
    STRING("String"),
    INTEGER("Integer"),
    BOOLEAN("Boolean"),
    UNLIMITED_NATURAL("Unlimited Natural"),
    REAL("Real");

    private String label;

    private UMLPropertyType(String label){
        this.label = label;
    }

    public String label() {
        return label;
    }
}
