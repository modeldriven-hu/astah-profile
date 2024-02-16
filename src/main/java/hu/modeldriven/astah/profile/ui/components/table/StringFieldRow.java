package hu.modeldriven.astah.profile.ui.components.table;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringFieldRow extends FieldRow<String> {

    public StringFieldRow(String label, Supplier<String> getValueFunction, Consumer<String> setValueFunction) {
        super(label, getValueFunction, setValueFunction);
    }
}
