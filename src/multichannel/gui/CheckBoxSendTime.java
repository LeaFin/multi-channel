package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JCheckBox;

/**
 *
 * @author Stephan
 * 
 * Adds a checkbox with the 4 Message-Typs
 * 
 */
public class CheckBoxSendTime extends JCheckBox implements ActionListener {

    PanelSendTime panel3;

    public CheckBoxSendTime(PanelSendTime panel3) {

        this.panel3 = panel3;
        setText("Später senden?");
        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSelected() == true) {
            panel3.setElementsVisible(true);
        } else {
            panel3.setElementsVisible(false);
            panel3.setSendTimeText( Calendar.getInstance() );
        }
    }
}
