/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import multichannel.exception.NoValidEmailException;
import multichannel.exception.NoValidPrinterException;
import multichannel.exception.NoFittingSubclassException;
import multichannel.exception.NoRecipientsException;
import multichannel.exception.NoValidNumberException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author leandrafinger
 */
public class MessageQueueManager {
    
   private Collection<Message> messageQueue;
   private Contact owener;
   
   /**
    * Constructor, creating the ArrayList in which all Messages, which are ready
    * to send, are stored.
    */
   public MessageQueueManager(){
       messageQueue = new ArrayList<Message>();
   }
   
   public void setOwener(Contact contact){
       owener = contact;
       Message.setSender(owener);
   }

    public Contact getOwener() {
        return owener;
    }
   
    /**
     * Creating an Email object and checking if it is valid. If it is, the email
     * gets added to the Message Queue.
     * 
     * @param recipients
     * @param text
     * @param images
     * @param sendTime 
     */
public void createEmail(Collection<Contact> recipients, String text,
           String subject, Collection<String> images, Calendar sendTime){
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString();
        createEmail(recipients, text, subject, images, sendTime, uid);
}
   
    public void createEmail(Collection<Contact> recipients, String text, 
                            String subject, Collection<String> images, Calendar sendTime, String uid){
       Email email = new Email(recipients, text, subject, sendTime, uid);
       Email.addToInstances(email);
       String response = "";
       try {
           boolean is_valid = email.validate();
           boolean img_valid = true;
           for (String img : images){
               img_valid = email.addImage(img) && img_valid;
           }
           if (is_valid){
               messageQueue.add(email);
               response = "Your email was successfully added to the Queue.";
           }
       }
       catch (NoRecipientsException e){
           response = e.getMessage();
       }
       catch (NoValidEmailException e){
           response = e.getMessage();
       }
       finally {
           System.out.println(response);
       }
    }
    
    
    /**
     * Creating an MMS object and checking if it is valid. If it is, the MMS
     * gets added to the Message Queue.
     * 
     * @param recipients
     * @param text
     * @param subject
     * @param images
     * @param sendTime 
     */
    public void createMMS(Collection<Contact> recipients, String text,
           String subject, Collection<String> images, Calendar sendTime){
       MMS mms = new MMS(recipients, text, subject, sendTime);
       String response = "";
       try {
           boolean is_valid = mms.validate();
           boolean images_valid = true;
           if (images.size()>0){
               for (String img : images){
                   images_valid = images_valid && mms.addImage(img);
               }
           }
           if (is_valid && images_valid){
               messageQueue.add(mms);
               response = "Your MMS was successfully added to the Queue.";
           }
       }
       catch (NoRecipientsException e){
           response = e.getMessage();
       }
       catch (NoValidNumberException e){
           response = e.getMessage();
       }
       finally {
           System.out.println(response);
       }
    }
    
    
      /**
     * Creating an SMS object and checking if it is valid. If it is, the SMS
     * gets added to the Message Queue.
     * 
     * @param recipients
     * @param text
     * @param sendTime
     */
    public void createSMS(Collection<Contact> recipients, String text, Calendar sendTime){
       Sms sms = new Sms(recipients, text, sendTime);
       String response = "";
       
       try {
           boolean text_valid = sms.validateText();
           boolean is_valid = sms.validate();
           if (is_valid && text_valid){
               messageQueue.add(sms);
               response = "Your SMS was successfully added to the Queue.";
           }
       }
       catch (NoRecipientsException e){
           response = e.getMessage();
       }
       catch (NoValidNumberException e){
           response = e.getMessage();
       }
       finally {
           System.out.println(response);
       }
    }
    
    
    
    
     /**
     * Creating an Print object and checking if it is valid. If it is, the print
     * gets added to the Message Queue.
     * 
     * @param recipients
     * @param text
     * @param images
     * @param sendTime 
     */
    public void createPrint(Collection<Contact> recipients, String text, String subject, Collection<String> images, Calendar sendTime){
       PrintedMessage print = new PrintedMessage(recipients, text, subject, sendTime);
       String response = "";
       try {
           boolean is_valid = print.validate();
           boolean images_valid = true;
           for (String img : images){
               images_valid = images_valid && print.addImage(img);
           }
           if (is_valid && images_valid){
               messageQueue.add(print);
               response = "Your Printjob was successfully added to the Queue.";
           }
       }
       catch (NoRecipientsException e){
           response = e.getMessage();
       }
       catch (NoValidPrinterException e){
           response = e.getMessage();
       }
       finally {
           System.out.println(response);
       }
    }
    
    /**
     *This method checks each messeage in the Queue, if it's sendTime is before the actual time.
     * If it is, the methode, which tells the message to send itself is called.
     * And if that was successful the message is getting removed from the queue.
     * TODO: check if it was successful!
     */
    public void getSendableMessages(){
        Calendar now = Calendar.getInstance();
        Iterator<Message> messages = messageQueue.iterator();
        while (messages.hasNext()){
            Message message = messages.next();
            if (message.getSendTime().compareTo(now) <= 0){
                boolean sent = sendMessage(message);
                if (sent){
                    messages.remove();
                }
            }
        }
    }
    
    /**
     * Method checks which subclass of the Message class the message object,
     * which is passed as parameter, is an instance of and the object is getting 
     * casted to this type and it's method send() is called. 
     * 
     * @param message The Message object which shoud be sent.
     * @return Retruns true if the message is sent, false if an error ocured.
     * @throws NoFittingSubclassException, if none of the isInstance checks returns true.
     */
    public boolean sendMessage(Message message){
        try {
            message.send();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
