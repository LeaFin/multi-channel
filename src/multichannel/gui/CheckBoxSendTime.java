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

    PanelSendTime panel3;

    public CheckBoxSendTime(PanelSendTime panel3) {

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
