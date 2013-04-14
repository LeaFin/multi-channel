
package multichannel;

/**
 *
 * @author stephanzumbühl
 * 
 */

class NotValidNumberException extends Exception {
    public NotValidNumberException(String number){
        super("The following Phonenumber isn't valid:" + number);
    }
}
