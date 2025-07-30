package net.allaoua.digitalbanking.exceptions;

public class CustomerNotFoundException extends  RuntimeException{

    public CustomerNotFoundException(String message){
        super(message);
    }
}
