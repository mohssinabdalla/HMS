/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *Thrown when no medical record exist for given patient
 * @author mommi
 */
public class RecordNotFoundException extends HMSException {
    public RecordNotFoundException(String message) {
        super(message);
    }
    
    
}
