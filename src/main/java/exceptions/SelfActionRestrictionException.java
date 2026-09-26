/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author hp
 */
public class SelfActionRestrictionException extends HMSException {
    // e.g. Admin attempting to edit/delete a DIFFERENT Admin account
    public SelfActionRestrictionException(String message) { super(message); }
}
