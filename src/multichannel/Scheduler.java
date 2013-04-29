/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leandrafinger
 */
public class Scheduler{
     
    private MessageQueueManager queueManager;
    private ArrayList<String> calendars;
    private Calendar lastImport = Calendar.getInstance();
    private Timer timer = new Timer();
    
    public Scheduler(){
        queueManager = new MessageQueueManager();
    }
    
    public void checkQueue(){
        queueManager.getSendableMessages();
    }
    
    public void importFromCalendar(){
        Calendar now = Calendar.getInstance();
        for (String calendar : calendars) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(calendar));
                String zeichen = null;
                String importetCalendar = "";
                while ((zeichen = reader.readLine()) != null) {
                    importetCalendar = importetCalendar + zeichen + "NewLine";
                }
                reader.close();
                ArrayList<HashMap> mails = parseCalendar(importetCalendar);
                for (HashMap mail: mails){
                    createReminder(mail);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lastImport = now;
    }
    
    public ArrayList<HashMap> parseCalendar(String cal){
        String[] events = cal.replaceAll("(BEGIN:VEVENT)(.*)(END:VEVENT)", "$2$3").split("(END:VEVENT)");
        
        ArrayList<HashMap> mails = new ArrayList<HashMap>();
        
        for (String event : events){
            Calendar lastMod = Calendar.getInstance();
            String lastModString = event.replace("(LAST-MODIFIED:)(.*)(NewLine)", "$2");
            if (lastModString.isEmpty()){
                lastModString = event.replace("(CREATED:)(.*)(NewLine)", "$2");
            }
            int[] dateInt = parseTime(lastModString);
            lastMod.set(dateInt[0], dateInt[1], dateInt[2], dateInt[3], dateInt[4], dateInt[5]);
            
            if (lastMod.compareTo(lastImport) >= 0 ){
                ArrayList<HashMap> contacts = new ArrayList<HashMap>(); 
                String[] contactStrings = event.replaceAll("(ATTENDEE)(.*)(ATTENDEE|CREATED)(.*)", "$2$3").split("(ATTENDEE)");
                HashMap mail = new HashMap();
                
                for (String contact: contactStrings){
                    HashMap<String, String> contactData = new HashMap<String, String>();
                    
                    if (contact.matches(".*CN.*")){
                        contactData.put("name", contact.replaceFirst("(.*CN=)(.*)(;.*)", "$2"));
                    }
                   
                    if (contact.matches(".*mailto.*")){
                        contactData.put("email", contact.replaceFirst("(.*mailto:)([\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4})", "$2"));
                    }
                    contacts.add(contactData);
                }
                
                ArrayList<Contact> recipients = new ArrayList<Contact>();
                
                for (HashMap contactData: contacts){
                    try {
                        Contact c = Contact.getByName((String)contactData.get("name"));
                        recipients.add(c);
                    }
                    catch (NoContactException ex) {
                        try {
                            Contact c = Contact.getByEmail((String)contactData.get("email"));
                            recipients.add(c);
                        }
                        catch (NoContactException e) {
                            if (!contactData.isEmpty()){
                                System.out.println(contactData + "does not exist in your Contacts.");
                            }
                        }
                    }
                }
                
                mail.put("recipients", recipients);
                
                /*Get start and end Date*/
                
                String startDateString = event.replace("(DTSTART[.]*:)(.*)(NewLine)", "$2");
                int[] dateStartInt = parseTime(startDateString);
                Calendar startDate = Calendar.getInstance();
                startDate.set(dateStartInt[0], dateStartInt[1], dateStartInt[2], dateStartInt[3], dateStartInt[4], dateStartInt[5]);
                
                String endDateString = event.replace("(DTEND[.]*:)(.*)(NewLine)", "$2");
                int[] dateEndInt = parseTime(startDateString);
                Calendar endDate = Calendar.getInstance();
                startDate.set(dateEndInt[0], dateEndInt[1], dateEndInt[2], dateEndInt[3], dateEndInt[4], dateEndInt[5]);
                
                /* sendTime */
                
                Calendar sendTime = (Calendar) startDate.clone();
                sendTime.add(Calendar.DAY_OF_MONTH, -1);
                
                mail.put("sendTime", sendTime);
                
                /*Get Summary*/
                
                String summary = event.replace("(SUMMARY:)(.*)(NewLine)", "$2");
                
                mail.put("summary", summary);
                
                /*Get description */
                
                String description = event.replace("(DESCRIPTION:)(.*)(NewLine)", "$2");
                
                /* make message */
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, yyyy-MM-dd HH:mm:ss");
                String eol = System.getProperty("line.separator");
                String message = "From: " + sdf.format(startDate) + eol
                        + "To: " + sdf.format(endDate) + eol + eol
                        + description.replaceAll("/n", eol).replaceAll("NewLine", " ");
                
                mail.put("message", message);
                
                mails.add(mail);
            }
            
        }
        
        return mails;
    }
    
    public void createReminder(HashMap mail){
        queueManager.createEmail((Collection<Contact>) mail.get("recipients"), (String) mail.get("message"), (String) mail.get("subject"), mail.get("sendTime"));
    }
    
    public int [] parseTime(String dateString){
        int[] date = null;
        date[0] = Integer.parseInt(dateString.substring(0, 3));
        date[1] = Integer.parseInt(dateString.substring(4, 5))-1;
        date[2] = Integer.parseInt(dateString.substring(6, 7));
        date[3] = Integer.parseInt(dateString.substring(9,10));
        date[4] = Integer.parseInt(dateString.substring(11,12));
        date[5] = Integer.parseInt(dateString.substring(13,14));
        return date;
    }
    
    // main methode must be in here to use Timer together with scheduler methods.
    
}
