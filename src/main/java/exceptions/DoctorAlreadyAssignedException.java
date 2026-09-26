/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author hp
 */
public class DoctorAlreadyAssignedException extends HMSException {
    // Assigning a doctor who already has an active manager assignment
    public DoctorAlreadyAssignedException(String message) { super(message); }
}

