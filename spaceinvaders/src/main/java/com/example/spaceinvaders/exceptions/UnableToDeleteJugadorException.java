package com.example.spaceinvaders.exceptions;
import java.io.Serial;

public class UnableToDeleteJugadorException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public UnableToDeleteJugadorException(String mensaje)
    {
        super(mensaje);
    }
    
}
