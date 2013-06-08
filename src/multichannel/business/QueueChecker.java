/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import java.util.TimerTask;

/**
 * A timer Task to tell the messageQueue to check if there are Messages to send.
 * @author leandrafinger
 */
public class QueueChecker extends TimerTask {
    
    private MessageQueueManager queueManager;

    public void setQueueManager(MessageQueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        queueManager.getSendableMessages();
    }
    
}
