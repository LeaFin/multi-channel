/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author leandrafinger
 */
public class PrintedMessage extends Message implements ImageAddable {
    
    private Collection<BufferedImage> images;
    
    public PrintedMessage(Collection<Contact> recipients, String text){
        super(recipients, text);
        images = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean send() {
        Collection<Printer> printers = super.getPrinters();
        for (Printer printer: printers){
            String addresse = printer.getAddresse();
            /*TODO print out all content*/
        }
        
        return false;
        
    }

    @Override
    public void addImage(BufferedImage img) {
        if (validateImage(img)) {
            images.add(img);
        }
        else {
            /* TODO throw Error */
        }
    }

    @Override
    public boolean validateImage(BufferedImage img) {
        /*TODO check size */
        return false;
    }

    @Override
    public boolean validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
