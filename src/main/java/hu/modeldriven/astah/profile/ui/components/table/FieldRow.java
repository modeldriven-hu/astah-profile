package hu.modeldriven.astah.profile.ui.components.table;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldRow<T> {

    private final String label;

    private final Supplier<T> getValueFunction;

    private final Consumer<T> setValueFunction;

    public FieldRow(String label,
                    Supplier<T> getValueFunction,
                    Consumer<T> setValueFunction){
        this.label = label;
        this.getValueFunction = getValueFunction;
        this.setValueFunction = setValueFunction;
    }

    public String getLabel() {
        return label;
    }

    public T getValue(){
        return getValueFunction.get();
    }

    public void setValue(T value){
        setValueFunction.accept(value);
    }

}
