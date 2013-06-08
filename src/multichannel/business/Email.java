
package multichannel.business;

import multichannel.exception.NoValidEmailException;
import multichannel.exception.NoRecipientsException;
import multichannel.exception.NoSuchUIDException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 * Subclass of Message
 *
 * @author leandrafinger
 */
public class Email extends Message implements ImageAddable {
    
    private String subject;
    private List<String> encodedImages;
    private String uid;
    private static HashMap<String, Email> instances = new HashMap<String, Email>();
    
    public Email(Collection<Contact> recipients, String text, String subject, Calendar sendTime, String uid){
        super(recipients, text, sendTime);
        this.subject = subject;
        encodedImages = new ArrayList<String>();
        this.uid = uid;
        addToInstances(this);
    }
    
    /**
     * All emails are stored in a HashMap with an id as key.
     * With this class method it's possible to get one specific email.
     * As input it takes the id of the wished email.
     * If the uid isn't existing in the map, a NoSuchUIDException is thrown.
     * @param uid
     * @return
     * @throws NoSuchUIDException
     */
    public static Email getByUid(String uid) throws NoSuchUIDException{
        if(instances.containsKey(uid)){
            return instances.get(uid);
        }
        throw new NoSuchUIDException();
    }
    
    /**
     * Classmethod to add an email to the HashMap.
     * Is called when a new email object is created.
     * @param email
     */
    public static void addToInstances(Email email){
        instances.put(email.getUid(), email);
    }
    
    /**
     * This method costructs the representation of an email, when it is sent.
     * 
     * @return String packedMessage, representing the header and content of mail.
     */
    @Override
    public String pack() {
        String to = "";
        String eol = System.getProperty("line.separator");
        for(Contact recipient : super.getRecipients()){
            to += recipient.getName();
            to += " <";
            to += recipient.getEmail();
            to += "> ";
        }
        Contact sender = super.getSender();
        String from = sender.getName() + " <" + sender.getEmail() + ">";
        String packedMessage = "===============================================" + eol
                + "Appended images: " + encodedImages + eol
                + "===============================================" + eol
                + "FORM: " + from + eol
                + "TO: " + to + eol
                + "SUBJECT: " + subject + eol
                + "===============================================" + eol
                + super.getText() + eol
                + "===============================================" + eol + eol + eol;
        return packedMessage;
    }

    @Override
    public String toString() {
        return "Email: " + subject;
    }

    /**
     * Adds an image to the images list.
     * The image is added as base64 string, as it is usual for an appended image
     * in a email.
     * @param path
     * @return
     */
    @Override
    public boolean addImage(String path) {
        BufferedImage img;
        String encodedImage = "";
        try {
            img = ImageIO.read(new File(path));
            encodedImage = encodeImage(img);
            encodedImages.add(encodedImage);
            return true;
        } catch (IOException ex) {
            System.out.println("Your image couldn't be added.");
            return false;
        }
    }
    
    /**
     * This method is called when an image gets added.
     * It take's the buffered image as input and returns an base64 encoded image
     * @param img
     * @return String representing the image, base64 encoded.
     * @throws IOException
     */
    public String encodeImage(BufferedImage img) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        baos.flush();
        Base64 base = new Base64();
        String encodedImage = base.encodeToString(baos.toByteArray());
        baos.close();
        encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
        return encodedImage;
    }

    /**
     * Checking if there are recipients defined and valid.
     * If there are no Recipients in the List. The NoRecipientsException is thrown.
     * If a emailaddresse doesn't match the regex, the NotValidEmailException is thrown.
     * @return boolean true, if everything validates
     * @throws NoRecipientsException
     * @throws NoValidEmailException
     */
    @Override
    public boolean validate() throws NoRecipientsException, NoValidEmailException {
        super.validateRecipients();
        for (String email : super.getContactEmails()){
            if (email.trim().isEmpty() || ! email.matches("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$")){
                throw new NoValidEmailException(email);
            }
        }
        return true;
    }

    /**
     * Getter method for the uid of an email.
     * @return
     */
    public String getUid() {
        return uid;
    }

    /**
     * Setter method for the suject.
     * Is used when an event in the imported calendar chaned.
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
