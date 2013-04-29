/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author leandrafinger
 */
public class MessageQueueManager {
    
   private Collection<Message> messageQueue;
   
   /**
    * Constructor, creating the ArrayList in which all Messages, which are ready
    * to send, are stored.
    */
   public MessageQueueManager(){
       messageQueue = new ArrayList<Message>();
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
           String subject, Collection<BufferedImage> images, Calendar sendTime){
        createEmail(recipients, text, subject, images, sendTime, "");
    }
   
    public void createEmail(Collection<Contact> recipients, String text,
           String subject, Collection<BufferedImage> images, Calendar sendTime, String uid){
       Email email;
       if (uid.equals("")){
           email = new Email(recipients, text, subject, sendTime);
       }
       else {
           email = new Email(recipients, text, subject, sendTime, uid);
       }
       Email.addToInstances(email);
       String response = "";
       try {
           boolean is_valid = email.validate();
           boolean images_valid = true;
           for (BufferedImage img : images){
               images_valid = images_valid && email.validateImage(img);
           }
           if (is_valid && images_valid){
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
           System.out.print(response);
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
           String subject, Collection<BufferedImage> images, Calendar sendTime){
       MMS mms = new MMS(recipients, text, subject, sendTime);
       String response = "";
       try {
           boolean is_valid = mms.validate();
           boolean images_valid = true;
           for (BufferedImage img : images){
               images_valid = images_valid && mms.validateImage(img);
           }
           if (is_valid && images_valid){
               messageQueue.add(mms);
               response = "Your MMS was successfully added to the Queue.";
           }
       }
       catch (NoRecipientsException e){
           response = e.getMessage();
       }
       catch (NotValidNumberException e){
           response = e.getMessage();
       }
       finally {
           System.out.print(response);
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
       catch (NotValidNumberException e){
           response = e.getMessage();
       }
       finally {
           System.out.print(response);
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
    public void createPrint(Collection<Contact> recipients, String text, String subject, Collection<BufferedImage> images, Calendar sendTime){
       PrintedMessage print = new PrintedMessage(recipients, text, subject, sendTime);
       String response = "";
       try {
           boolean is_valid = print.validate();
           boolean images_valid = true;
           for (BufferedImage img : images){
               images_valid = images_valid && print.validateImage(img);
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
           System.out.print(response);
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
            if (message.getSendTime().compareTo(now) >= 0){
                try {
                    boolean sent = sendMessage(message);
                    if (sent){
                        messages.remove();
                    }
                }
                catch (NoFittingSubclassException e){
                    System.out.println(e.getMessage());
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
    public boolean sendMessage(Message message) throws NoFittingSubclassException {
        for (Class<? extends Message> klass : Message.getMessageTypes()){
            if (klass.isInstance(message)){
                klass.cast(message);
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
        throw new NoFittingSubclassException();
    }

    void createEmail(Collection<Contact> collection, String string, String string0, Object get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
