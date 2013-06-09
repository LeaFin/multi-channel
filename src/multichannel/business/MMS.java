
package multichannel.business;

import java.awt.Image;
import multichannel.exception.NoRecipientsException;
import multichannel.exception.NoValidNumberException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.imageio.ImageIO;

/**
 * Subclass of Message
 * @author leandrafinger
 */
public class MMS extends Message implements ImageAddable, Serializable {
    
    private Collection<Image> images;
    private String subject;
    
    
    public MMS(Collection<Contact> recipients, String text, String subject, Calendar sendTime){
        super(recipients, text, sendTime);
        images = new ArrayList<Image>();
        this.subject = subject;
    }
    
    /**
     * Get's all needed information, which must be sent within an mms.
     * Putting them together in a nice representation.
     * @return Message representation as string
     */
    @Override
    public String pack(){
        String to = "";
        String eol = System.getProperty("line.separator");
        for(Contact recipient : super.getRecipients()){
            to += recipient.getName();
            to += " <";
            to += recipient.getPhone();
            to += "> ";
        }
        Contact sender = super.getSender();
        String from = sender.getName() + " <" + sender.getPhone() + ">";
        String packedMessage = "===============================================" + eol
                + "FORM: " + from + eol
                + "TO: " + to + eol
                + "SUBJECT: " + subject + eol
                + "===============================================" + eol
                + super.getText() + eol
                + images + eol
                + "===============================================" + eol + eol + eol;
        return packedMessage;
    }

    /**
     * Adds an image to the mms.
     * If it is to wide, it'll be scaled down. so it can be sent.
     * @param path
     * @return boolean, true if adding was successful
     */
    @Override
    public boolean addImage(String path) {
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Your image couldn't be added.");
            return false;
        }
        if (validateImageSize(img)) {
            images.add(img);
        }
        else {
            Image new_img = img.getScaledInstance(160, -1, Image.SCALE_SMOOTH);
            images.add(new_img);
        }
        return true;
    }

    /**
     * Checking if an image has the allowed size.
     * @param img
     * @return boolean
     */
    public boolean validateImageSize(BufferedImage img) {
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
            if (number.trim().isEmpty() || ! number.matches("[+0]{1}0?(41)?7[6789]{1}[0-9]{7}")){
                throw new NoValidNumberException(number);
            }
        }
        return true;
    }

    
}
