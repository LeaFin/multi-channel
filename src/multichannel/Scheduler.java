package multichannel;

import multichannel.business.MessageQueueManager;
import multichannel.business.CalendarImport;
import multichannel.business.QueueChecker;
import multichannel.business.Contact;
import java.util.Timer;
import java.util.TimerTask;
import multichannel.business.ContactList;
import multichannel.exception.ContactInvalidException;
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
    
    /**
     * Sets up the message queue
     * Deserializes the ContactList
     * Initializes the GUI
     * Creating a Scheduler instance to handle the timer tasks
     * Sets up the timertasks CalenderImport and QueueChecker
     * @param args
     */
    public static void main(String[] args) throws ContactInvalidException{
        ContactList contactList = ContactList.deserializeContacts();
        Contact owener = null;
        if (contactList.getContacts().isEmpty()){
            owener = contactList.createNewContact("Owener", "0762381938", "owener@gmail.com", "\\\\OwenersServer\\OwnersPrinter");
        }
        else{
            owener = contactList.getContacts().get(0);
        }
        Scheduler scheduler = new Scheduler();
        MessageQueueManager queueManager = MessageQueueManager.deserializeMessages();
        queueManager.setOwener(owener);
        
        // GUI zeichnen
        GuiStart mcgui = new GuiStart(scheduler);
    	mcgui.creategui();
        
        // Kalenderimport starten
        CalendarImport calendarImport = (CalendarImport)scheduler.getCalendarImport();
        calendarImport.setContactList(contactList);
        calendarImport.setQueueManager(queueManager);
        calendarImport.addCalendar("kalenderimport.ics");
        scheduler.schedule(calendarImport, 0, 600000);
        
        //queueChecker starten
        QueueChecker queueChecker = (QueueChecker)scheduler.getQueueChecker();
        queueChecker.setQueueManager(queueManager);
        scheduler.schedule(queueChecker, 0, 20000);
    }
    
}
