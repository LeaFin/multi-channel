/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author leandrafinger
 */
public abstract class Message {
    
    private Collection<Contact> recipients;
    private String text;
    private Contact sender;
    
    public Message(Collection<Contact> recipients, String text){
        this.recipients = recipients;
        this.text = text;
    }

    public void setRecipients(Collection<Contact> recipients) {
        this.recipients = recipients;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Contact getSender() {
        return sender;
    }

    public void setSender(Contact sender) {
        this.sender = sender;
    }

    public Collection<Contact> getRecipients() {
        return recipients;
    }

    public String getText() {
        return text;
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
    
    public abstract boolean send();
    public abstract boolean validate();
}
