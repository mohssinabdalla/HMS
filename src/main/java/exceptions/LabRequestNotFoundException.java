/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 * Thrown when looking up or updating a LabRequest by an id that
 * doesn't exist
 * @author mommi
 */
public class LabRequestNotFoundException extends HMSException {
    public  LabRequestNotFoundException(String message) {
        super(message);
    }
    
    
}
