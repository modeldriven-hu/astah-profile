package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLProfile;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProfileTableModel extends AbstractTableModel {

    private final UMLProfile profile;

    private final List<FieldRow<String>> fields;

    public ProfileTableModel(UMLProfile profile){
        this.profile = profile;
        this.fields = new ArrayList<>();
        this.fields.add(new FieldRow<>("name", profile::name, profile::modifyName ));
        this.fields.add(new FieldRow<>("uri", profile::uri, profile::modifyUri ));
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
            case 0: return "Property";
            case 1: return "Value";
        }

        return "<undefined>";
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
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1 && aValue instanceof String) {
            fields.get(rowIndex).setValue((String) aValue);
        }
    }
}
