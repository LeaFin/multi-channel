package multichannel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import multichannel.Scheduler;
import multichannel.business.CalendarImport;
import multichannel.business.ContactList;
import multichannel.business.MessageQueueManager;

public class GuiStart {
    
    // Hauptframe
    private final JFrame frame = new JFrame();
    
    Scheduler scheduler;
                  
    // übergreifende variabel
    private String inputVerzStr;
    private int sendtyp = 1; // Standardmässig E-Mail, wie Radio Button auswahl
                  
    //  panels generien lassen
    PanelControl panel1 = new PanelControl(this); 
    PanelMessageTyp panel2 = new PanelMessageTyp(this);
    PanelSendTime panel3 = new PanelSendTime();  
    PanelTextforMessage panel4 = new PanelTextforMessage(); 
    PanelAddImage panel5 = new PanelAddImage(this);
      
    public GuiStart(Scheduler scheduler) {
        // Übergabe Scheduler
        this.scheduler = scheduler;
    }
    
    public void creategui() {
        
        
        // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hinzufügen von Menü-Leiste
        JMenuBar menuBar = new Menu(this);
        frame.setJMenuBar(menuBar);
 
        // Center-Panel, worin alle externe Panels kommen
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
        
        if (this.sendtyp == 2){
            panel5.setVisibility(false);
            panel4.setVisibility(false);
            panel4.setMessageInfo("Maximal 160 Zeichen!");
        } else {
            panel5.setVisibility(true);
            panel4.setVisibility(true);
            panel4.setMessageInfo("");
        }
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
     
    public String getSubjectText() {
        return panel4.getSubject();
    }
     
     public Calendar getSendTimeText() {
        return panel3.getSendTimeText();
    }
     
     public MessageQueueManager getMessageQueueManager() {
         CalendarImport calImport = (CalendarImport)scheduler.getCalendarImport();
         return calImport.getQueueManager();
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


    public ContactList getContactList() {
        CalendarImport calImport = (CalendarImport)scheduler.getCalendarImport();
        return calImport.getContactList();
    }
    
    public Collection<String> getPicturePath() {
        Collection<String> tempcol = new ArrayList<String>();
        String picturePath = panel5.getPP();
        if (!picturePath.trim().isEmpty()){
            tempcol.add(picturePath);
        }
        return tempcol;
    }


    
}
