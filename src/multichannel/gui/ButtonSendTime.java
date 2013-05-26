/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Stephan
 */
public class ButtonSendTime extends JButton {

    private final PanelSendTime panel3;
    private Calendar sendtime;

    public ButtonSendTime(final PanelSendTime panel3) {
        this.panel3 = panel3;

        setText("Datum");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // DATUM via JDialog abfragen
                sendtime = new Datepicker().setPickedDate();
                // Zeit ins Feld schreiben
                // Vorher typ umwandeln
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                if (sendtime != null) {
                    panel3.setSendTimeText(sdf.format(sendtime.getTime()));
                }
            }
        });
    }
}
