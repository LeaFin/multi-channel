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
 * 
 * Generates the Panel for the Sendtime.
 * 
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

    /**
     * Set the Sendtime from the Datepicker.
     * 
     * @param sendtime
     */
    public void setSendTimeText(Calendar sendtime) {
        this.sendtime = sendtime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        this.textsendtime.setText(sdf.format(sendtime.getTime()));
        
    }
    
    /**
     * Returns the choosen sendtime
     * @return Calendar
     */
    public Calendar getSendTimeText() {
        return this.sendtime;
    }
    

    /**
     * Set Visibility of the Datepicker-Button and the sendtime.
     * true=Visible
     * 
     * @param tf
     */
    public void setElementsVisible(boolean tf){
    labelsendtime.setVisible(tf);
    textsendtime.setVisible(tf);
    buttonsendtime.setVisible(tf);
    }
    
 
}
