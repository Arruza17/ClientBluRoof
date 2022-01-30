/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Yeray Sampedro
 */
public class PhoneFormatException extends Exception {

    public PhoneFormatException() {
        super("Your phone number should contain the country code. E.G: +34643749874");
    }
   
    
}
