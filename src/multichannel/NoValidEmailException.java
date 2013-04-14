/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

/**
 *
 * @author leandrafinger
 */
class NoValidEmailException extends Exception {
    public NoValidEmailException(String email){
        super("The following email-addresse isn't valid:" + email);
    }
}
