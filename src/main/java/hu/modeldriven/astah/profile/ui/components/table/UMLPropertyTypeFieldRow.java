package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UMLPropertyTypeFieldRow extends EnumerationFieldRow {

    public UMLPropertyTypeFieldRow(String label, UMLProperty property) {
        super(label,
                () -> Arrays
                        .stream(UMLPropertyType.values())
                        .map(UMLPropertyType::label)
                        .collect(Collectors.toList()),

                () -> property.type().label(),

                s -> {
                    UMLPropertyType newType = UMLPropertyType.propertyType(s);
                    property.modifyType(newType);
                });
    }
}
