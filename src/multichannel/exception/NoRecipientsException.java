/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.exception;

/**
 *
 * @author leandrafinger
 */
public class NoRecipientsException extends Exception {
    public NoRecipientsException(){
        super("You didn't enter any Recipients.");
    }
}
