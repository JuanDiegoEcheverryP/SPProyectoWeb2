package com.example.spaceinvaders.exceptions;

import java.util.NoSuchElementException;

import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // AOP
public class MyCustomErrorController {

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleNotFound(Model model, NoSuchElementException exception) {
        model.addAttribute("exceptionText", exception.toString());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleErrorNoParam(Model model, MissingServletRequestParameterException exception) {
        model.addAttribute("exceptionText", exception.toString());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(RepeatedCoordinateException.class)
    public ModelAndView handleCoord(Model model, RepeatedCoordinateException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(RepeatedNameException.class)
    public ModelAndView handleNombre(Model model, RepeatedNameException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(UnableToDeletePlanetaException.class)
    public ModelAndView handlePlanetaBorrar(Model model, UnableToDeletePlanetaException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(NotNullException.class)
    public ModelAndView handleNulls(Model model, NotNullException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(OutOfLimitsException.class)
    public ModelAndView handleOutOfLimits(Model model, OutOfLimitsException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(RepeatedStockException.class)
    public ModelAndView handleRepeatedStock(Model model, RepeatedStockException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }

    @ExceptionHandler(UnableToDeleteJugadorException.class)
    public ModelAndView handleExisteNave(Model model, UnableToDeleteJugadorException exception) {
        model.addAttribute("exceptionText", exception.getMessage());
        return new ModelAndView("pagina-error");
    }
    
}   