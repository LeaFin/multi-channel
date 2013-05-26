/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import java.awt.Image;
import multichannel.exception.NoRecipientsException;
import multichannel.exception.NoValidNumberException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.imageio.ImageIO;

/**
 *
 * @author leandrafinger
 */
public class MMS extends Message implements ImageAddable {
    
    private Collection<Image> images;
    private String subject;
    
    
    public MMS(Collection<Contact> recipients, String text, String subject, Calendar sendTime, Contact sender){
        super(recipients, text, sendTime, sender);
        images = new ArrayList<Image>();
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
    public boolean addImage(String path) {
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Your image couldn't be added.");
            return false;
        }
        if (validateImage(img)) {
            images.add(img);
        }
        else {
            Image new_img = img.getScaledInstance(160, -1, Image.SCALE_SMOOTH);
            images.add(new_img);
        }
        return true;
    }

    public boolean validateImage(BufferedImage img) {
        if (img.getWidth() <= 160){
            return true;
        }
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
    public boolean validate() throws NoRecipientsException, NoValidNumberException {
        // Check if any Recipient given
        super.validateRecipients();
    
        // Check for correct Numbers of each Contact
        for (String number : super.getNumbers()){
            // RegEx selfmade... checks minimum Swiss-Numbers like +41792873890 or 0792873890 or 0041792873890
            if (number.trim().isEmpty() || ! number.matches("[(+41)(0041)0]?[(76)(77)(78)(79)]?[0-9]{7}")){
                throw new NoValidNumberException(number);
            }
        }
        return true;
    }

    
}
