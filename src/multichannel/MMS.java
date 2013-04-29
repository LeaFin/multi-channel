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
    private String subject;
    
    
    public MMS(Collection<Contact> recipients, String text, String subject, Calendar sendTime){
        super(recipients, text, sendTime);
        images = new ArrayList<BufferedImage>();
        this.subject = subject;
    }

    @Override
    public boolean send() {
        Collection<String> numbers = super.getNumbers();
        /* TODO send for each recipient */
        for(String number: numbers){
            System.out.print(this);
        }
        return true;
    }

    @Override
    public void addImage(String path) {
        BufferedImage img = null;
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

    
    
     /**
     * Checking if there are recipients defined and valid.
     * If there are no Recipients in the List. The NoRecipientsException is thrown.
     * If a Phonenumber doesn't match the regex, the NotValidNumberException is thrown.
     * @return boolean true, if everything validates
     * @throws NoRecipientsException
     * @throws NotValidNumberException
     */
    @Override
    public boolean validate() throws NoRecipientsException, NotValidNumberException {
        // Check if any Recipient given
        super.validateRecipients();
    
        // Check for correct Numbers of each Contact
        for (String number : super.getNumbers()){
            // RegEx selfmade... checks minimum Swiss-Numbers like +41792873890 or 0792873890 or 0041792873890
            if (number.trim().isEmpty() || ! number.matches("[(+41)(0041)0]?[(76)(77)(78)(79)]?[0-9]{7}")){
                throw new NotValidNumberException(number);
            }
        }
        return true;
    }

    
}
