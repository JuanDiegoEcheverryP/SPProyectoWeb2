package com.example.spaceinvaders.exceptions;

import java.io.Serial;

public class RepeatedCoordinateException extends Exception {
    
    @Serial 
    private static final long serialVersionUID=1;

    public RepeatedCoordinateException(String mensaje)
    {
        super(mensaje);
    }
    
}
