package com.example.spaceinvaders.exceptions;

import java.io.Serial;

public class RepeatedNameException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public RepeatedNameException(String mensaje)
    {
        super(mensaje);
    }
    
}
