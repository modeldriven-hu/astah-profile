package hu.modeldriven.core.uml.command;

import java.io.File;

public class LoadProfileFromFileCommand implements ProfileCommand{

    private final File file;

    public LoadProfileFromFileCommand(File file){
        this.file = file;
    }

    @Override
    public void execute(){

    }
}
