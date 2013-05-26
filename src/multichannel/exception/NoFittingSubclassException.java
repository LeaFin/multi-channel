/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.exception;

/**
 *
 * @author leandrafinger
 */
public class NoFittingSubclassException extends Exception {

    public NoFittingSubclassException() {
        super("The message couldn't be sent, cause the messag type is illegal.");
    }
    
}
