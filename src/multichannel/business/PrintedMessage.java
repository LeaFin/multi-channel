/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import multichannel.exception.NoValidPrinterException;
import multichannel.exception.NoRecipientsException;
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
public class PrintedMessage extends Message implements ImageAddable {
    
    private Collection<BufferedImage> images;
    private String subject;
    
    public PrintedMessage(Collection<Contact> recipients, String text, String subject, Calendar sendTime){
        super(recipients, text, sendTime);
        images = new ArrayList<BufferedImage>();
        this.subject = subject;
    }

    @Override
    public boolean addImage(String path) {
        BufferedImage img;
        try{
            img = ImageIO.read(new File(path));
            images.add(img);
        } catch (IOException ex) {
            System.out.println("Your image couldn't be added.");
            return false;
        }
        return true;
    }

    /**
     * Checking if there are recipients defined and valid.
     * If there are no Recipients in the List. The NoRecipientsException is thrown.
     * If a Printer doesn't match the regex, the NotValidNumberException is thrown.
     * @return boolean true, if everything validates
     * @throws NoRecipientsException
     * @throws NoValidPrinterException
     */
    @Override
    public boolean validate() throws NoRecipientsException, NoValidPrinterException {
        // Check if any Recipient given
        super.validateRecipients();
    
        // Check for correct Printer of each Contact
        for (String printer : super.getPrinters()){
            // RegEx selfmade... i dont know what to check for a printer... it check now something like this: \\Server1\PrinterName
            // naja klapped noned... regex macht no ken sinn...
          //  if (! printer.matches("((\\){2})\w*(\\){1}\w*")){
           //     throw new NoValidPrinterException(printer);
           // }
        }
        return true;
    }

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
                + "FORM: " + from + eol
                + "TO: " + to + eol
                + "SUBJECT: " + subject + eol
                + "===============================================" + eol
                + super.getText() + eol
                + images + eol
                + "===============================================" + eol + eol + eol;
        return packedMessage;
    }
}
