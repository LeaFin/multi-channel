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
    
    public Sms(Collection<Contact> recipients, String text, Calendar sendTime, Contact sender){
        super(recipients, text, sendTime, sender);
    }

    @Override
    public boolean send() {
        Collection<String> numbers = super.getNumbers();
        for(String number : numbers){
            System.out.print(this);
        }
        return true;
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

    
     /**
     * Checking if there are recipients defined and valid.
     * If there are no Recipients in the List. The NoRecipientsException is thrown.
     * If a Phonenumber doesn't match the regex, the NotValidNumberException is thrown.
     * @return boolean true, if everything validates
     * @throws NoRecipientsException
     * @throws NotValidNumberException
     */
    @Override
    public boolean validate() throws NoRecipientsException, NotValidNumberException {
        // Check if any Recipient given
        super.validateRecipients();
    
        // Check for correct Numbers of each Contact
        for (String number : super.getNumbers()){
            // RegEx selfmade... checks minimum Swiss-Numbers like +41792873890 or 0792873890 or 0041792873890
            if (number.trim().isEmpty() || ! number.matches("[(+41)(0041)0]?[(76)(77)(78)(79)]?[0-9]{7}")){
                throw new NotValidNumberException(number);
            }
        }
        return true;
    }
}
