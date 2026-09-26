/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author hp
 */
public class AssetUnavailableException extends HMSException{
      // Allocating a room/ward/lab/imaging room already occupied
    public AssetUnavailableException(String message) { super(message); }
}
