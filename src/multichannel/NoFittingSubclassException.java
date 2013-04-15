/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

/**
 *
 * @author leandrafinger
 */
class NoFittingSubclassException extends Exception {

    public NoFittingSubclassException() {
        super("The message couldn't be sent, cause the messag type is illegal.");
    }
    
}
