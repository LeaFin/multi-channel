/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import java.util.TimerTask;

/**
 *
 * @author leandrafinger
 */
public class QueueChecker extends TimerTask {
    
    private MessageQueueManager queueManager;
    
    public QueueChecker(MessageQueueManager queueManager){
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        queueManager.getSendableMessages();
    }
    
}
