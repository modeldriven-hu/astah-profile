package hu.modeldriven.astah.axmz;

public interface AxmzFile {
    AstahProject project() throws AstahProjectImportFailedException;
}
