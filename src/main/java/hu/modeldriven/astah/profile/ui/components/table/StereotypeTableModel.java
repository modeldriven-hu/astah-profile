package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StereotypeTableModel extends AbstractTableModel {

    private final transient List<FieldRow<String>> fields;

    public StereotypeTableModel(UMLStereotype stereotype) {
        this.fields = new ArrayList<>();
        this.fields.add(new StringFieldRow("name", stereotype::name, stereotype::modifyName));
        this.fields.add(new UMLMetaClassFieldRow("metaClass", stereotype));
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
        if (columnIndex == 0) {
            return String.class;
        } else {
            return FieldRow.class;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
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

        switch (columnIndex) {
            case 0:
                return fields.get(rowIndex).getLabel();
            case 1:
                return fields.get(rowIndex);
            default:
                return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1 && aValue instanceof String) {
            fields.get(rowIndex).setValue((String) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
