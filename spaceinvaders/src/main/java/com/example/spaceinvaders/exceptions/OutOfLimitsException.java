package com.example.spaceinvaders.exceptions;

import java.io.Serial;

public class OutOfLimitsException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public OutOfLimitsException(String mensaje)
    {
        super(mensaje);
    }
    
}
