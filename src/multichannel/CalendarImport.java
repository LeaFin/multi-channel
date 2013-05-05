/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leandrafinger
 */
public class CalendarImport extends TimerTask {
    
    private ArrayList<String> calendars = new ArrayList<String>();
    private Calendar lastImport = Calendar.getInstance();
    private MessageQueueManager queueManager;
    private Lock lock = new ReentrantLock();
    
    public CalendarImport(MessageQueueManager queueManager){
        this.queueManager = queueManager;
    }
    
    public void addCalendar(String path){
        calendars.add(path);
    }
    
    @Override
    public void run() {
        System.out.println("CalendarImport statet.");
        if(lock.tryLock()){
            importFromCalendar();
            lock.unlock();
        }
        else{
            System.out.println("CalendarImport is locked.");
        }
    }
    
    private void importFromCalendar(){
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
        System.out.println("CalendarImport ist beendet.");
    }
    
    private ArrayList<HashMap> parseCalendar(String cal){
        String[] events = cal.split("(BEGIN:VEVENT)");
        ArrayList<HashMap> mails = new ArrayList<HashMap>();
        
        events[0] = "";
        
        for (String event : events){
            if (event.isEmpty()){
                continue;
            }
            Calendar lastMod = Calendar.getInstance();
            String lastModString = event.replaceFirst("(.*)(LAST-MODIFIED:)(.*?)(NewLine)(.*)", "$3");
            if (lastModString.isEmpty()){
                lastModString = event.replaceFirst("(.*)(CREATED:)(.*?)(NewLine)(.*)", "$3");
            }
            int[] dateInt = parseTime(lastModString);
            lastMod.set(dateInt[0], dateInt[1], dateInt[2], dateInt[3], dateInt[4], dateInt[5]);
            
            if (lastMod.compareTo(lastImport) <= 0 ){
                event = event.replaceAll("BEGIN:VALARM(.*?)END:VALARM", "");
                ArrayList<HashMap> contacts = new ArrayList<HashMap>();
                
                String[] contactStrings = event.split("(ATTENDEE)");
                contactStrings[0]="";
                HashMap mail = new HashMap();
                
                String uid = event.replace("(.*)(UID:)(.*?)(NewLine)(.*)", "$3");
                mail.put("uid", uid);
                
                for (String contact: contactStrings){
                    if (contact.isEmpty()){
                        continue;
                    }
                    HashMap<String, String> contactData = new HashMap<String, String>();
                    
                    if (contact.matches(".*CN.*")){
                        contactData.put("name", contact.replaceFirst("(.*)(.*CN=)(.*?)(;.*)", "$3"));
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
                
                String startDateString = event.replaceFirst("(.*)(DTSTART[^0-9]*:)(.*?)(NewLine)(.*)", "$3");
                int[] dateStartInt = parseTime(startDateString);
                Calendar startDate = Calendar.getInstance();
                startDate.set(dateStartInt[0], dateStartInt[1], dateStartInt[2], dateStartInt[3], dateStartInt[4], dateStartInt[5]);
                
                String endDateString = event.replaceFirst("(.*)(DTEND[^0-9]*:)(.*?)(NewLine)(.*)", "$3");
                int[] dateEndInt = parseTime(startDateString);
                Calendar endDate = Calendar.getInstance();
                endDate.set(dateEndInt[0], dateEndInt[1], dateEndInt[2], dateEndInt[3], dateEndInt[4], dateEndInt[5]);
                
                /* sendTime */
                
                Calendar sendTime = (Calendar) startDate.clone();
                sendTime.add(Calendar.DAY_OF_MONTH, -1);
                
                mail.put("sendTime", sendTime);
                
                /*Get Summary*/
                
                String summary = event.replaceFirst("(.*)(SUMMARY:)(.*?)(NewLine)(.*)", "$3");
                
                mail.put("summary", summary);
                
                /*Get description */
                
                String description = event.replaceFirst("(.*)(DESCRIPTION:)(.*?)(NewLine)(.*)", "$3");
                
                /* make message */
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, yyyy-MM-dd HH:mm:ss");
                String eol = System.getProperty("line.separator");
                String message = "From: " + sdf.format(startDate.getTime()) + eol
                        + "To: " + sdf.format(endDate.getTime()) + eol + eol
                        + description.replaceAll("/n", eol).replaceAll("NewLine", " ");
                
                mail.put("message", message);
                
                mails.add(mail);
            }
            
        }
        
        return mails;
    }
    
    private void createReminder(HashMap mail){
        String uid = (String) mail.get("uid");
        Collection<Contact> recipients = (Collection<Contact>) mail.get("recipients");
        recipients.add(queueManager.getOwener());
        String subject = (String) mail.get("summary");
        String message = (String) mail.get("message");
        Calendar sendTime = (Calendar) mail.get("sendTime");
        try {
            Email email = Email.getByUid(uid);
            email.setRecipients(recipients);
            email.setSubject(subject);
            email.setText(message);
            email.setSendTime(sendTime);
        } catch (NoSuchUIDException ex) {
            queueManager.createEmail(recipients, message, subject, new ArrayList<BufferedImage>() , sendTime, uid);
        }
    }
    
    private int [] parseTime(String dateString){
        int[] date = new int[6];
        int l = dateString.length();
        date[0] = Integer.parseInt(dateString.substring(0, 4));
        date[1] = Integer.parseInt(dateString.substring(4, 6))-1;
        date[2] = Integer.parseInt(dateString.substring(6, 8));
        if (l > 8){
            date[3] = Integer.parseInt(dateString.substring(9, 11));
            date[4] = Integer.parseInt(dateString.substring(11, 13));
            date[5] = Integer.parseInt(dateString.substring(13, 15));
        }
        else {
            date [3] = 0;
            date [4] = 0;
            date [5] = 0;
        }
        return date;
    }
    
}
