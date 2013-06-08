package multichannel.business;

import multichannel.exception.NoRecipientsException;
import multichannel.exception.NoValidNumberException;
import java.util.Calendar;
import java.util.Collection;

/**
 * Subclass of Message
 * @author leandrafinger
 */
public class Sms extends Message {
    
    public Sms(Collection<Contact> recipients, String text, Calendar sendTime){
        super(recipients, text, sendTime);
    }

    /**
     * Collecting all information which need to be sent.
     * And arranging them for a print.
     * @return
     */
    @Override
    public String pack(){
        String to = "";
        String eol = System.getProperty("line.separator");
        for(Contact recipient : super.getRecipients()){
            to += recipient.getName();
            to += " <";
            to += recipient.getPhone();
            to += "> ";
        }
        Contact sender = super.getSender();
        String from = sender.getName() + " <" + sender.getPhone() + ">";
        String packedMessage = "===============================================" + eol
                + "FORM: " + from + eol
                + "TO: " + to + eol
                + "===============================================" + eol
                + super.getText() + eol
                + "===============================================" + eol + eol + eol;
        return packedMessage;
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
    public boolean validate() throws NoRecipientsException, NoValidNumberException {
        // Check if any Recipient given
        super.validateRecipients();
    
        // Check for correct Numbers of each Contact
        for (String number : super.getNumbers()){
            // RegEx selfmade... checks minimum Swiss-Numbers like +41792873890 or 0792873890 or 0041792873890
            if (number.trim().isEmpty() || ! number.matches("[+0]{1}0?(41)?7[6789]{1}[0-9]{7}")){
                throw new NoValidNumberException(number);
            }
        }
        return true;
    }
}
