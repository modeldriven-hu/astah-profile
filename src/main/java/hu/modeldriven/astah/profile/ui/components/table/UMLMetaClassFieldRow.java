package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UMLMetaClassFieldRow extends EnumerationFieldRow {

    public UMLMetaClassFieldRow(String label, UMLStereotype stereotype) {
        super(label,
                () -> Arrays
                        .stream(UMLMetaClass.values())
                        .filter(x -> !x.equals(UMLMetaClass.UNKNOWN))
                        .map(UMLMetaClass::label)
                        .collect(Collectors.toList()),

                () -> stereotype.metaClass().label(),

                s -> {
                    UMLMetaClass newMetaClass = UMLMetaClass.metaClass(s);
                    stereotype.modifyMetaClass(newMetaClass);
                }
        );
    }
}
