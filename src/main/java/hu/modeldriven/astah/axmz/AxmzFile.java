package hu.modeldriven.astah.axmz;

import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;

public class AxmzFile {

    private final File file;

    public AxmzFile(File file) {
        this.file = file;
    }

    public void upgradeProfile(String profileName, UMLProfile profile) {
        //https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch12s03.html
        //https://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html
    }

}
