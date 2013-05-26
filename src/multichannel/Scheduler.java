/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import multichannel.business.Printer;
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
     
    private MessageQueueManager queueManager;
    private TimerTask calendarImport;
    private TimerTask queueChecker;
    private ContactList contactList;
    
    public Scheduler(ContactList contactList){
        queueManager = new MessageQueueManager();
        this.contactList = contactList;
        calendarImport = new CalendarImport(queueManager, contactList);
        queueChecker = new QueueChecker(queueManager);
    }

    public TimerTask getCalendarImport() {
        return calendarImport;
    }

    public TimerTask getQueueChecker() {
        return queueChecker;
    }

    public MessageQueueManager getQueueManager() {
        return queueManager;
    }

    public ContactList getContactList() {
        return contactList;
    }
    
    // main methode must be in here to use Timer.
    public static void main(String[] args){
        ContactList contactList = ContactList.deserializeContacts();
        Contact owener = null;
        if (contactList.getContacts().isEmpty()){
            owener = contactList.createNewContact("Owener", "076 238 19 38", "abc@gmail.com", new Printer("name", "addresse"));
        }
        else{
            owener = contactList.getContacts().get(0);
        }
        Scheduler scheduler = new Scheduler(contactList);
        contactList = ContactList.deserializeContacts();
        MessageQueueManager messageQueue = scheduler.getQueueManager();
        messageQueue.setOwener(owener);
        TimerTask queueChecker = scheduler.getQueueChecker();
        
        // GUI zeichnen
        GuiStart mcgui = new GuiStart(scheduler);
    	mcgui.creategui();
        
        // Kalenderimport starten
        CalendarImport calendarImport = (CalendarImport)scheduler.getCalendarImport();
        calendarImport.addCalendar(System.getProperty("user.dir") + "/testimport.ics");
        scheduler.schedule(queueChecker, 0, 60000);
        scheduler.schedule(calendarImport, 0, 600000);

    }
    
}
