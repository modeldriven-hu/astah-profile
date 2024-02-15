package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;

public class UMLPropertyFieldRow extends FieldRow<String> {

    public UMLPropertyFieldRow(String label, UMLProperty property) {
        super(label, () -> property.type().label(), s -> {
            UMLPropertyType newType = UMLPropertyType.propertyType(s);
            property.modifyType(newType);
        });
    }
}
