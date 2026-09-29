/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *  Thrown when a prescription is issued with an empty patient ID
 * @author mommi
 */
public class InvalidPrescriptionException extends HMSException {
    public InvalidPrescriptionException(String message) {
        super(message);
    }
    
}
