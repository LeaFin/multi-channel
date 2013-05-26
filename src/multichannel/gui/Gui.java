package multichannel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import multichannel.business.MessageQueueManager;

public class Gui {

    // Hauptframe
    private final JFrame frame = new JFrame();
     
    // INSTANZ Manager
    private MessageQueueManager queueManager;
     
    // übergreifende variabel
    private String inputVerzStr;
    private int sendtyp = 1; // Standardmässig E-Mail, wie Radio Button auswahl
    
    // panels generien lassen
    Panel1 panel1 = new Panel1(this);
    Panel2 panel2 = new Panel2(this);
    Panel3 panel3 = new Panel3();
    Panel4 panel4 = new Panel4();
    Panel5 panel5 = new Panel5(this);
    
 
    public void creategui() {

        // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
* Hinzufügen von Menü-Leiste
*/
        JMenuBar menuBar = new Menu();
        frame.setJMenuBar(menuBar);
 
        // Center-Panel, worin alle Panel kommen
        JPanel pa_center = new JPanel();

        // GUI Zusammensetzen
        frame.getContentPane().add(BorderLayout.CENTER, pa_center);
        pa_center.setLayout(new BoxLayout(pa_center, BoxLayout.Y_AXIS));
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(panel1);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(panel2);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(panel3);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(panel4);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(panel5);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));

        // Frame anpassen und sichtbar machen
        frame.getContentPane().setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);

    }

    public void setSendTyp(int sendtyp) {
        this.sendtyp = sendtyp;
    }
    
      public int getSendTyp() {
        return this.sendtyp;
    }

    public JFrame getFrame() {
        return this.frame;
    }
    
     public String getMessageText() {
        return panel4.getMessage();
    }
     
     public String getSendTimeText() {
        return panel3.getSendTimeText();
    }
     
     public MessageQueueManager getMessageQueueManager() {
        return queueManager;
    }

    public void setPicturePath(String labelpicturepath) {
        this.inputVerzStr = labelpicturepath;
    }

    /*
* Returns the Picture-Path as a String
*/
    public String getInputVerzStr() {
        return this.inputVerzStr;
    }

    public Gui(MessageQueueManager queueManager) {
        this.queueManager = queueManager;
    }
}