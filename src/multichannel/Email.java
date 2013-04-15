/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 *
 * @author leandrafinger
 */
public class Email extends Message implements ImageAddable {
    
    private String subject;
    private Collection<BufferedImage> images; //Still needed?
    private List<String> encodedImages;
    
    public Email(Collection<Contact> recipients, String text, String subject, Calendar sendTime){
        super(recipients, text, sendTime);
        this.subject = subject;
        images = new ArrayList<BufferedImage>();
        encodedImages = new ArrayList<String>();
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
        String packedMessage = "===============================================" + eol
                + "FORM: @  " + eol
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
    public void addImage(String path) {
        BufferedImage img;
        String encodedImage;
        try {
            img = ImageIO.read(new File(path));
            encodedImage = encodeImage(img);
            encodedImages.add(encodedImage);
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Your image couldn't be added.");
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

    @Override
    public boolean validateImage(BufferedImage img) {
        /* TODO check size */
        return false;
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
    
    
    // Was macht das da, Printer?? Chume nöd druuus.... Ste
    public static void main(String[] args){
        Printer printer = new Printer("name", "addresse");
        Contact con = new Contact("Name", "sdf", "rasf", printer);
        Contact con2 = new Contact("Name2", "sdf2", "rasafaeadeiraojf", printer);
        ArrayList<Contact> contacts = new ArrayList();
        contacts.add(con);
        contacts.add(con2);
        Email email = new Email(contacts, "NachrichtenInhalt", "Subject", Calendar.getInstance());
        System.out.println(Message.getMessageTypes());
        System.out.print(email.send());
    }
}
