/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import multichannel.exception.NoRecipientsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author leandrafinger
 */
public abstract class Message {
    
    private Collection<Contact> recipients;
    private String text;
    private static Contact sender;
    private Calendar sendTime; 
    private static List<Class<? extends Message>> messageTypes = Arrays.asList(Email.class, Sms.class, MMS.class, PrintedMessage.class); 
    
    public Message(Collection<Contact> recipients, String text, Calendar sendTime, Contact sender){
        this.recipients = recipients;
        this.text = text;
        this.sendTime = sendTime;
        this.sender = sender;
    }

    public void setRecipients(Collection<Contact> recipients) {
        this.recipients = recipients;
    }

    public static Contact getSender() {
        return sender;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public Collection<Contact> getRecipients() {
        return recipients;
    }

    public String getText() {
        return text;
    }

    public Calendar getSendTime() {
        return sendTime;
    }

    public void setSendTime(Calendar sendTime) {
        this.sendTime = sendTime;
    }

    public static List<Class<? extends Message>> getMessageTypes() {
        return messageTypes;
    }
    
    
    /**
     * Returns Collection of e-mail addresses for all Recipients of this Message.
     */
    public Collection<String> getContactEmails() {
        Collection<String> emails = new ArrayList<String>();
        for (Contact recipient: recipients){
            emails.add(recipient.getEmail());
        }
        return emails;
    }

    public Collection<String> getNumbers() {
        Collection<String> numbers = new ArrayList<String>();
        for (Contact recipient: recipients){
            numbers.add(recipient.getPhone());
        }
        return numbers;
    }
    
    public Collection<Printer> getPrinters() {
        Collection<Printer> printers = new ArrayList<Printer>();
        for (Contact recipient: recipients){
            printers.add(recipient.getPrinter());
        }
        return printers;
    }
    
    public boolean validateRecipients() throws NoRecipientsException{
        if (recipients.isEmpty()){
            throw new NoRecipientsException();
        }
        return true;
    }
    
    public abstract boolean send();
    public abstract boolean validate() throws Exception;
}
