/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.util.Calendar;
import java.util.Collection;

/**
 *
 * @author leandrafinger
 */
public class Sms extends Message {
    
    public Sms(Collection<Contact> recipients, String text, Calendar sendTime){
        super(recipients, text, sendTime);
    }

    @Override
    public boolean send() {
        Collection<String> numbers = super.getNumbers();
        /* TODO send for each recipient */
        return false;
    }
    
    
    /**
     * Checking if the Text of the sms isn't too long to send.
     * If the text is too long the return statement is true.
     * @return boolean
     */
    public boolean validateText() {
        String text = super.getText();
        if (text.length() > 160){
            return false;
        }
        return true;
    }

    @Override
    public boolean validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
