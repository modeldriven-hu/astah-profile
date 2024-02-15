package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    public UMLMetaClass metaClass(String value){
        return UMLMetaClass.valueOf(value);
    }

}
