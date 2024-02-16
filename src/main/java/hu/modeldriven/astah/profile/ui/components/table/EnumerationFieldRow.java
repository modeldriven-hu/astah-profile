package hu.modeldriven.astah.profile.ui.components.table;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnumerationFieldRow extends StringFieldRow {

    private final Supplier<List<String>> listFunction;

    public EnumerationFieldRow(String label, Supplier<List<String>> listFunction, Supplier<String> getValueFunction, Consumer<String> setValueFunction) {
        super(label, getValueFunction, setValueFunction);
        this.listFunction = listFunction;
    }

    public List<String> getValues() {
        return listFunction.get();
    }

}
