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
    
    public Message(Collection<Contact> recipients, String text, Calendar sendTime){
        this.recipients = recipients;
        this.text = text;
        this.sendTime = sendTime;
    }

    public void setRecipients(Collection<Contact> recipients) {
        this.recipients = recipients;
    }

    public static Contact getSender() {
        return sender;
    }
    
    public static void setSender(Contact sender){
        Message.sender = sender;
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
    
    public Collection<String> getPrinters() {
        Collection<String> printers = new ArrayList<String>();
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
    
    public boolean send(){
        Class<? extends Message> klass = this.getClass();
        klass.cast(this);
        String klassName = klass.getSimpleName();
        Collection<String> addresses;
        if (klassName.equals("Sms") || klassName.equals("MMS")){
            addresses = getNumbers();
        }
        else if(klassName.equals("Email")){
            addresses = getContactEmails();
        }
        else {
            addresses = getPrinters();
        }
        String packedMessage = pack();
        for(String addresse: addresses){
            System.out.println(klass.getName() + " sent to: " + addresse);
            System.out.println(packedMessage);
        }
        return true;
    };
    public abstract String pack();
    public abstract boolean validate() throws Exception;
 }