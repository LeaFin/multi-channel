/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    private Timer timer = new Timer();
    
    public Scheduler(){
        queueManager = new MessageQueueManager();
    }
    
    public void checkQueue(){
        queueManager.getSendableMessages();
    }
    
    public void importFromCalendar(){
        for (String calendar : calendars) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(calendar));
                String zeichen = null;
                String importetCalendar = "";
                while ((zeichen = reader.readLine()) != null) {
                    importetCalendar = importetCalendar + zeichen + "NewLine";
                }
                reader.close();
                parseCalendar(importetCalendar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void parseCalendar(String cal){
        String[] events = cal.replaceAll("(BEGIN:VEVENT)(.*)(END:VEVENT)", "$2$3").split("(END:VEVENT)");
        for (String event : events){
            HashMap<String, String> contactData = new HashMap<String, String>();
            String[] contacts = event.replaceAll("(ATTENDEE)(.*)(ATTENDEE|CREATED)(.*)", "$2$3").split("(ATTENDEE)");
            for (String contact: contacts){
                if (contact.matches(".*CN.*")){
                    contactData.put("name", contact.replaceFirst("(.*CN=)(.*)(;.*)", "$2"));
                }
                if (contact.matches(".*mailto.*")){
                    contactData.put("email", contact.replaceFirst("(.*mailto:)([\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4})", "$2"));
                }
                System.out.println(contactData);
            }
        }
    }
    
    // main methode must be in here to use Timer together with scheduler methods.
    
}
