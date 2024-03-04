package com.example.spaceinvaders.exceptions;

import java.io.Serial;

public class RepeatedStockException extends Exception {
    @Serial 
    private static final long serialVersionUID=1;

    public RepeatedStockException(String mensaje)
    {
        super(mensaje);
    }
    
}
