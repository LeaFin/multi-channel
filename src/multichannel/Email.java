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
public class Email extends Message implements ImageAddable {
    
    private String subject;
    private Collection<BufferedImage> images;
    
    public Email(Collection<Contact> recipients, String text, String subject){
        super(recipients, text);
        this.subject = subject;
        images = new ArrayList<BufferedImage>();
    }

    @Override
    public boolean send() {
        Collection<String> emailAddresses = super.getContactEmails();
        /*TODO add method which grabs and packs all elements of the mail ev. with html */
        for(String emailAddresse: emailAddresses){
            System.out.print(this);
        }
        return true;
    }

    @Override
    public String toString() {
        return "Email: " + subject;
    }

    @Override
    public void addImage(BufferedImage img) {
        images.add(img);
    }

    @Override
    public boolean validateImage(BufferedImage img) {
        /* TODO check size */
        return false;
    }

    @Override
    public boolean validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
