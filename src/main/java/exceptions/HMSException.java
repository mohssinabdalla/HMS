/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Base class for every custom exception in the HMS system.
 * Allows broad catching (catch HMSException) when the specific type
 * doesn't matter, or specific catching when it does.
 */
public class HMSException extends Exception{
    
    public HMSException (String message){
        super(message);
    }
    
}
