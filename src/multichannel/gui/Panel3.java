/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan
 */
public class Panel3 extends JPanel {

    private JLabel labelsendtime, textsendtime;
    private JButton buttonsendtime;

    public Panel3() {




    // CheckBox erzeugen
        JCheckBox checksendtime = new CheckBoxSendTime(this);

    // Zuschaltbar, unsichtbar am Anfang
        // Sendezeit:
        labelsendtime = new JLabel("Sendezeit: ");
        labelsendtime.setVisible(false);
        // Textfeld mit sendezeit
        textsendtime = new JLabel("sofort");
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

    public void setSendTimeText(String sendtime) {
        this.textsendtime.setText(sendtime);
    }
    
    public String getSendTimeText() {
        return this.textsendtime.getText();
    }
    

    public void setElementsVisible(boolean tf){
    labelsendtime.setVisible(tf);
    textsendtime.setVisible(tf);
    buttonsendtime.setVisible(tf);
    }
    
 
}
