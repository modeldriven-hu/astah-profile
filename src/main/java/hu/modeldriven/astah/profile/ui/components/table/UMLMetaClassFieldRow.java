package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.Arrays;
import java.util.List;

public class UMLMetaClassFieldRow extends FieldRow<String> {

    public UMLMetaClassFieldRow(String label, UMLStereotype stereotype) {
        super(label, () -> stereotype.metaClass().label(), s -> {
            UMLMetaClass newMetaClass = UMLMetaClass.metaClass(s);
            stereotype.modifyMetaClass(newMetaClass);
        });
    }

    public List<UMLMetaClass> getMetaClasses() {
        return Arrays.asList(UMLMetaClass.values());
    }

    public UMLMetaClass metaClass(String value) {
        return UMLMetaClass.valueOf(value);
    }

}
