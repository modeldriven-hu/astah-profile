package hu.modeldriven.astah.axmz.impl;

import java.nio.file.Path;

public interface AxmzModelPart {

    boolean appliesTo(Path file);

    void process(Path file);

}
