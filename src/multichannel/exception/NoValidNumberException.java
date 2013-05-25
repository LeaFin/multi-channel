
package multichannel.exception;

/**
 *
 * @author stephanzumbühl
 * 
 */

public class NoValidNumberException extends Exception {
    public NoValidNumberException(String number){
        super("The following Phonenumber isn't valid:" + number);
    }
}
