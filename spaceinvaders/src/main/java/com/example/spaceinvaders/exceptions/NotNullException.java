package com.example.spaceinvaders.exceptions;

import java.io.Serial;

public class NotNullException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public NotNullException(String mensaje)
    {
        super(mensaje);
    }
    
}
