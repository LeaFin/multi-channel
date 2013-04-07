/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

/**
 *
 * @author leandrafinger
 */
class NoRecipientsException extends Exception {
    public NoRecipientsException(){
        super("You didn't enter any Recipients.");
    }
}
