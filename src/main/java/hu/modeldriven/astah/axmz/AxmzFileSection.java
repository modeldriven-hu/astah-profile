package hu.modeldriven.astah.axmz;

import java.io.File;
import java.nio.file.Path;

public interface AxmzFileSection {

    boolean appliesTo(Path file);

    void process(Path file);

}
