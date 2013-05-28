/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import multichannel.business.MessageQueueManager;
import multichannel.business.CalendarImport;
import multichannel.business.QueueChecker;
import multichannel.business.Contact;
import java.util.Timer;
import java.util.TimerTask;
import multichannel.business.ContactList;
import multichannel.gui.GuiStart;

/**
 *
 * @author leandrafinger
 */
public class Scheduler extends Timer{
     
    private TimerTask calendarImport;
    private TimerTask queueChecker;
    
    public Scheduler(){
        calendarImport = new CalendarImport();
        queueChecker = new QueueChecker();
    }

    public TimerTask getCalendarImport() {
        return calendarImport;
    }

    public TimerTask getQueueChecker() {
        return queueChecker;
    }
    
    // main methode must be in here to use Timer.
    public static void main(String[] args){
        ContactList contactList = ContactList.deserializeContacts();
        Contact owener = null;
        if (contactList.getContacts().isEmpty()){
            owener = contactList.createNewContact("Owener", "0762381938", "abc@gmail.com", "192.168.0.3");
        }
        else{
            owener = contactList.getContacts().get(0);
        }
        Scheduler scheduler = new Scheduler();
        MessageQueueManager queueManager = new MessageQueueManager();
        queueManager.setOwener(owener);
        
        // GUI zeichnen
        GuiStart mcgui = new GuiStart(scheduler);
    	mcgui.creategui();
        
        // Kalenderimport starten
        CalendarImport calendarImport = (CalendarImport)scheduler.getCalendarImport();
        calendarImport.setContactList(contactList);
        calendarImport.setQueueManager(queueManager);
        calendarImport.addCalendar(System.getProperty("user.dir") + "/testimport.ics");
        scheduler.schedule(calendarImport, 0, 600000);
        
        //queueChecker starten
        QueueChecker queueChecker = (QueueChecker)scheduler.getQueueChecker();
        queueChecker.setQueueManager(queueManager);
        scheduler.schedule(queueChecker, 0, 20000);
    }
    
}
