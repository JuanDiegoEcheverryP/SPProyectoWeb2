package com.example.spaceinvaders.exceptions;
import java.io.Serial;

public class UnableToDeletePlanetaException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public UnableToDeletePlanetaException(String mensaje)
    {
        super(mensaje);
    }
    
}
