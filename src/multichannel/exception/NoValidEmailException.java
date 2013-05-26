/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.exception;

/**
 *
 * @author leandrafinger
 */
public class NoValidEmailException extends Exception {
    public NoValidEmailException(String email){
        super("The following email-addresse isn't valid:" + email);
    }
}
