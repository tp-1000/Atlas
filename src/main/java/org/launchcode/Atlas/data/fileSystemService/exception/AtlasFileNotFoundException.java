package org.launchcode.Atlas.data.fileSystemService.exception;


public class AtlasFileNotFoundException extends RuntimeException{
    //private String message;

    public AtlasFileNotFoundException(String message) {
        super(message);
    }
}
