/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Thrown when an out of range blood pressure, heart rate or temperature is 
 * while logging in a patient vitals
 * @author mommi
 */
public class InvalidVitalSignException extends HMSException {
    public InvalidVitalSignException(String message) {
            super(message);
    }
    
}
