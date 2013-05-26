/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan
 */
public class PanelSendTime extends JPanel {

    Calendar sendtime;
    private JLabel labelsendtime, textsendtime;
    private JButton buttonsendtime;

    public PanelSendTime() {

    // CheckBox erzeugen
        JCheckBox checksendtime = new CheckBoxSendTime(this);

    // Zuschaltbar, unsichtbar am Anfang
        // Sendezeit:
        labelsendtime = new JLabel("Sendezeit: ");
        labelsendtime.setVisible(false);
        // Textfeld mit sendezeit
        textsendtime = new JLabel("");
        setSendTimeText(Calendar.getInstance());
        textsendtime.setVisible(false);

        // Button erzeugen
        buttonsendtime = new ButtonSendTime(this);
        buttonsendtime.setVisible(false);

        // Layout des Panel setzen
        setLayout(new FlowLayout(FlowLayout.LEFT));


        // Komponenten zum Panel hinzufügen
        add(checksendtime);

        // Unsichtbare Komonenten
        add(Box.createRigidArea(new Dimension(50, 0)));
        add(labelsendtime);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(textsendtime);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(buttonsendtime);
    }

    public void setSendTimeText(Calendar sendtime) {
        this.sendtime = sendtime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.textsendtime.setText(sdf.format(sendtime.getTime()));
        
    }
    
    public Calendar getSendTimeText() {
        return this.sendtime;
    }
    

    public void setElementsVisible(boolean tf){
    labelsendtime.setVisible(tf);
    textsendtime.setVisible(tf);
    buttonsendtime.setVisible(tf);
    }
    
 
}
