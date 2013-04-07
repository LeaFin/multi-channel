/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

/**
 *
 * @author leandrafinger
 */
class NotValidEmailException extends Exception {
    public NotValidEmailException(String email){
        super("The following email-addresse isn't valid:" + email);
    }
}
