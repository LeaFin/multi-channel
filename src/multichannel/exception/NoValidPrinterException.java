
package multichannel.exception;

/**
 *
 * @author stephanzumbühl
 * 
 */

public class NoValidPrinterException extends Exception {
    public NoValidPrinterException(String printer){
        super("The following Printer isn't valid:" + printer);
    }
}
