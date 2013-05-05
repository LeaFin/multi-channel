/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author leandrafinger
 */
public class Scheduler extends Timer{
     
    private MessageQueueManager queueManager;
    private TimerTask calendarImport;
    private TimerTask queueChecker;
    
    public Scheduler(){
        queueManager = new MessageQueueManager();
        calendarImport = new CalendarImport(queueManager);
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
    
    
    // main methode must be in here to use Timer.
    public static void main(String[] args){
        Scheduler scheduler = new Scheduler();
        Contact owener = new Contact("Name", "076 238 19 38", "abc@gmail.com", new Printer("name", "addresse"));
        MessageQueueManager messageQueue = scheduler.getQueueManager();
        messageQueue.setOwener(owener);
        TimerTask queueChecker = scheduler.getQueueChecker();
        CalendarImport calendarImport = (CalendarImport)scheduler.getCalendarImport();
        calendarImport.addCalendar(System.getProperty("user.dir") + "/testimport.ics");
        scheduler.schedule(queueChecker, 0, 60000);
        scheduler.schedule(calendarImport, 0, 600000);
    }
    
}
