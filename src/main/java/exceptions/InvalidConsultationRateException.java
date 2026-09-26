/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author hp
 */
public class InvalidConsultationRateException extends HMSException{
        // e.g. negative or zero baseAmount
    public InvalidConsultationRateException(String message) { super(message); }
}
