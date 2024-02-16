package hu.modeldriven.astah.profile.ui.components.table;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValueColumnCellEditor extends DefaultCellEditor implements ActionListener {

    private JTextField textField;

    private JComboBox<String> comboBox;

    public ValueColumnCellEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable jTable, Object value, boolean isSelected, int row, int column) {

        if (value instanceof EnumerationFieldRow) {

            EnumerationFieldRow fieldRow = (EnumerationFieldRow) value;

            this.comboBox = new JComboBox<>();
            fieldRow.getValues().forEach(comboBox::addItem);

            String currentElement = fieldRow.getValue();
            comboBox.setSelectedItem(currentElement);
            comboBox.setSelectedItem(this);

            comboBox.addActionListener(this);

            return comboBox;
        } else if (value instanceof StringFieldRow) {
            this.textField = new JTextField();
            textField.setText(((StringFieldRow) value).getValue());

            this.textField.addActionListener(this);
            return textField;
        }

        return super.getTableCellEditorComponent(jTable, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {

        if (comboBox != null && comboBox.isShowing()) {
            return comboBox.getSelectedItem();
        }

        if (textField != null && textField.isShowing()) {
            return textField.getText();
        }

        return "";
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.fireEditingStopped();
    }
}
