package hu.modeldriven.astah.axmz;

import java.io.File;
import java.net.URI;

public class ZipFile {

    private final File file;

    public ZipFile(File file) {
        this.file = file;
    }

    public File file() {
        return this.file;
    }

    public URI uri() {
        return URI.create("jar:file:" + file.getPath());
    }

}
