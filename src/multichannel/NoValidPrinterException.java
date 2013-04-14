
package multichannel;

/**
 *
 * @author stephanzumbühl
 * 
 */

class NoValidPrinterException extends Exception {
    public NoValidPrinterException(String printer){
        super("The following Printer isn't valid:" + printer);
    }
}
