/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author hp
 */
public class UnauthorizedAccessException extends HMSException {
      // Thrown when 
    // Examples:
    //   - Admin attempts to edit another Admin's profile (self-only rule)
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
