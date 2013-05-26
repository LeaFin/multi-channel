/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 *
 * @author leandrafinger
 */
public class Email extends Message implements ImageAddable {
    
    private String subject;
    private List<String> encodedImages;
    private String uid;
    private static List<Email> instances = new ArrayList<Email>();
    
    public Email(Collection<Contact> recipients, String text, String subject, Calendar sendTime, String uid, Contact sender){
        super(recipients, text, sendTime, sender);
        this.subject = subject;
        encodedImages = new ArrayList<String>();
        this.uid = uid;
    }
    
    public static Email getByUid(String uid) throws NoSuchUIDException{
        for (Email email: instances){
            if (email.getUid().equals(uid)){
                return email;
            }
        }
        throw new NoSuchUIDException();
    }
    
    public static void addToInstances(Email email){
        instances.add(email);
    }

    @Override
    public boolean send() {
        Collection<String> emailAddresses = super.getContactEmails();
        String packedMessage = pack();
        for(String emailAddresse: emailAddresses){
            System.out.println("Message sent to: "+ emailAddresse);
            System.out.println(packedMessage);
        }
        return true;
    }
    
    /**
     * 
     * @return String packedMessage, representing the header and content of mail.
     */
    public String pack() {
        // TODO: bilder ausgeben.
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
     * Don't know if it works yet.
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

    public String getUid() {
        return uid;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
