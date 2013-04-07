/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 *
 * @author leandrafinger
 */
public class MMS extends Message implements ImageAddable {
    
    private Collection<BufferedImage> images;
    
    public MMS(Collection<Contact> recipients, String text, Calendar sendTime){
        super(recipients, text, sendTime);
        images = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean send() {
        Collection<String> numbers = super.getNumbers();
        /* TODO send for each recipient */
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
