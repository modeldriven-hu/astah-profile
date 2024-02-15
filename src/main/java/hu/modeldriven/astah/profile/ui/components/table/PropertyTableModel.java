package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PropertyTableModel extends AbstractTableModel {

    private final UMLProperty property;

    private final List<FieldRow<String>> fields;

    public PropertyTableModel(UMLProperty property){
        this.property = property;
        this.fields = new ArrayList<>();
        this.fields.add(new FieldRow<>("name", property::name, property::modifyName ));
        this.fields.add(new FieldRow<>("type", () -> property.type().label(), s -> {
            UMLPropertyType newType = UMLPropertyType.propertyType(s);
            property.modifyType(newType);
        }));
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public int getRowCount() {
        return this.fields.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "Property";
            case 1:
                return "Value";
            default:
                return "<undefined>";
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex){
            case 0:
                return fields.get(rowIndex).getLabel();
            case 1:
                return fields.get(rowIndex).getValue();
            default:
                return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1 && aValue instanceof String) {
            fields.get(rowIndex).setValue((String) aValue);
        }
    }
}
