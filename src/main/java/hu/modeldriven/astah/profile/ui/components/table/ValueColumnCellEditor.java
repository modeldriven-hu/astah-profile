package hu.modeldriven.astah.profile.ui.components.table;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLPropertyType;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValueColumnCellEditor extends DefaultCellEditor implements ActionListener {

    private final JTextField textField;
    private final JComboBox<String> metaClassComboBox;
    private final JComboBox<String> propertyTypeComboBox;

    public ValueColumnCellEditor() {
        super(new JTextField());
        this.textField = new JTextField();

        this.metaClassComboBox = new JComboBox<>();

        for (UMLMetaClass metaClass : UMLMetaClass.values()) {
            metaClassComboBox.addItem(metaClass.label());
        }

        this.propertyTypeComboBox = new JComboBox<>();

        for (UMLPropertyType propertyType : UMLPropertyType.values()) {
            propertyTypeComboBox.addItem(propertyType.label());
        }

        this.metaClassComboBox.addActionListener(this);
        this.propertyTypeComboBox.addActionListener(this);
        this.textField.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable jTable, Object value, boolean isSelected, int row, int column) {

        if (value instanceof UMLMetaClassFieldRow) {
            String currentElement = ((UMLMetaClassFieldRow) value).getValue();
            metaClassComboBox.setSelectedItem(currentElement);
            return metaClassComboBox;
        } else if (value instanceof UMLPropertyTypeFieldRow) {
            String currentElement = ((UMLPropertyTypeFieldRow) value).getValue();
            propertyTypeComboBox.setSelectedItem(currentElement);
            return propertyTypeComboBox;
        } else if (value instanceof StringFieldRow) {
            textField.setText(((StringFieldRow) value).getValue());
            return textField;
        }

        return super.getTableCellEditorComponent(jTable, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {

        if (metaClassComboBox.isShowing()) {
            return metaClassComboBox.getSelectedItem();
        }

        if (propertyTypeComboBox.isShowing()) {
            return propertyTypeComboBox.getSelectedItem();
        }

        if (textField.isShowing()) {
            return textField.getText();
        }

        return "";
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.fireEditingStopped();
    }
}
