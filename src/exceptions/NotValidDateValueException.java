/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * NotValidDateValueException Exception
 * @author ander
 */
public class NotValidDateValueException extends Exception {
    /**
     * Constructor for the exception
     * @param msg the message to set
     */
    public NotValidDateValueException(String msg) {
        super(msg);
    }
    
}
