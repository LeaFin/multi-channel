/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Stephan
 */
public class CheckBoxSendTime extends JCheckBox implements ActionListener {

    Panel3 panel3;

    public CheckBoxSendTime(Panel3 panel3) {

        this.panel3 = panel3;

        setText("Später senden?");

        addActionListener(this);
        //addActionListener(actionListener);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isSelected() == true) {
            panel3.setElementsVisible(true);
        } else {
            panel3.setElementsVisible(false);
            panel3.setSendTimeText("sofort");
        }

        //Frame aktualisieren
        //frame.getContentPane().validate();
        //frame.getContentPane().repaint();
    }
}
