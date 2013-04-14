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
public class Email extends Message implements ImageAddable {
    
    private String subject;
    private Collection<BufferedImage> images;
    
    public Email(Collection<Contact> recipients, String text, String subject, Calendar sendTime){
        super(recipients, text, sendTime);
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

    /**
     * Checking if there are recipients defined and valid.
     * If there are no Recipients in the List. The NoRecipientsException is thrown.
     * If a emailaddresse doesn't match the regex, the NotValidEmailException is thrown.
     * @return boolean true, if everything validates
     * @throws NoRecipientsException
     * @throws NotValidEmailException
     */
    @Override
    public boolean validate() throws NoRecipientsException, NotValidEmailException {
        super.validateRecipients();
        for (String email : super.getContactEmails()){
            if (email.trim().isEmpty() || ! email.matches("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$")){
                throw new NotValidEmailException(email);
            }
        }
        return true;
    }
    
    public static void main(String[] args){
        Printer printer = new Printer("name", "addresse");
        Contact con = new Contact("Name", "sdf", "rasf", printer);
        ArrayList<Contact> contacts = new ArrayList();
        contacts.add(con);
        
        System.out.print(new Email(contacts, "asdf", "sasdf", Calendar.getInstance()));
        System.out.println(Message.getMessageTypes());
    }
}
