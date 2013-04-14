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
public class MessageQueueManager {
    
   private Collection<Message> messageQueue;
   
   /**
    * Constructor, creating the ArrayList in which all Massages, which are ready
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
     * @param subject
     * @param images
     * @param sendTime 
     */
    public void createEmail(Collection<Contact> recipients, String text,
           String subject, Collection<BufferedImage> images, Calendar sendTime){
       Email email = new Email(recipients, text, subject, sendTime);
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
       catch (NotValidEmailException e){
           response = e.getMessage();
       }
       finally {
           System.out.print(response);
       }
    }
    
    public void getSendableMessages(){
        Calendar now = Calendar.getInstance();
        for (Message message : messageQueue){
            if (message.getSendTime().compareTo(now) >= 0){
                for (Class<? extends Message> klass : Message.getMessageTypes()){
                    if (klass.isInstance(message)){
                        klass.cast(message);
                        message.send();
                    }
                }
            }
        }
    }
}
