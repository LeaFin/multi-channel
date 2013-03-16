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
public class MessageQueue {
    
   private Collection<Message> messages;
    
   public MessageQueue(){
       messages = new ArrayList<Message>();
   }
   
   public void createEmail(Collection<Contact> recipients, String text, String subject, Collection<BufferedImage> images){
       Email email = new Email(recipients, text, subject);
       if (email.validate()){
           messages.add(email);
       } 
   }
}
