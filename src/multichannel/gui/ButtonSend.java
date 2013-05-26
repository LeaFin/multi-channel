/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import multichannel.business.Contact;
import multichannel.exception.NoContactException;
import multichannel.exception.NoValidNumberException;

/**
 *
 * @author Stephan
 */
public class ButtonSend extends JButton implements ActionListener {

    GuiStart maingui;
    Panel1 panel1;

    public ButtonSend(Panel1 panel1, GuiStart maingui) {

        this.maingui = maingui;
        this.panel1 = panel1;


        setText("Senden");
        setPreferredSize(new Dimension(80, 50));

        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {



        // MAIL

        if (maingui.getSendTyp() != 2) {
            sendmultimedia();
        }

        // MMS
        if (maingui.getSendTyp() == 2) {
            sendmultimedia();
        }

    }

    private void sendsms() {
        JFrame msgframe = new JFrame();
        // Fehlerüberprüfung
            // Check: Anzahl Zeichen<=160 und mind. 1 Empfänger
            if (maingui.getMessageText().length() > 160 || panel1.getContactList().getSize() == 0) {
                // Zuviele Zeichen
                if (maingui.getMessageText().length() > 160) {
                    JOptionPane.showMessageDialog(msgframe,
                            "Ein SMS darf nicht mehr als 160 Zeichen haben! \n "
                            + "Bitte entferne "
                            + (maingui.getMessageText().length() - 160)
                            + " zeichen.",
                            "Mehr als 160 Zeichen",
                            JOptionPane.WARNING_MESSAGE);
                }
                // Keine Empfänger
                if (panel1.getContactList().getSize() == 0) {
                    JOptionPane.showMessageDialog(msgframe,
                            "Bitte füge einen Empfänger hinzu \n",
                            "Keine Empfänger",
                            JOptionPane.WARNING_MESSAGE);
                }

            } else {
                Calendar cldr = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                // Umwandeln der contactlist ins das Format Collection
                Collection<Contact> con = null;
                ArrayList<Contact> temparray = new ArrayList<Contact>();
                for (int i = 0; i < panel1.getContactList().getSize(); i++) {
                    temparray.add( (Contact) panel1.getContactList().get(i) );
                 }
                con = temparray;
                
                // Hier wird das SMS erstellt im QueeManager
                // Wie kann man response abfangen und ausgeben?
                maingui.getMessageQueueManager().createSMS(con, maingui.getMessageText(), cldr);

            }
    }

    private void sendmultimedia() {
        JFrame msgframe = new JFrame();
        // Fehlerüberprüfung
            // Check: Anzahl Zeichen<=160 und mind. 1 Empfänger
            if (panel1.getContactList().getSize() == 0) {
                // Keine Empfänger
                if (panel1.getContactList().getSize() == 0) {
                    JOptionPane.showMessageDialog(msgframe,
                            "Bitte füge einen Empfänger hinzu \n",
                            "Keine Empfänger",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                Calendar cldr = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                // Umwandeln der contactlist ins das Format Collection
                Collection<Contact> con = null;
                ArrayList<Contact> temparray = new ArrayList<Contact>();
                for (int i = 0; i < panel1.getContactList().getSize(); i++) {
                    temparray.add( (Contact) panel1.getContactList().get(i) );
                 }
                con = temparray;
                
                // Hier wird die Multimedia-Nachricht erstellt im QueeManager
                // Wie kann man response abfangen und ausgeben?
                if(maingui.getSendTyp() == 1){
                maingui.getMessageQueueManager().createEmail(con, maingui.getMessageText(), "Subject" , null ,cldr);
                }
                if(maingui.getSendTyp() == 3){
                maingui.getMessageQueueManager().createMMS(con, maingui.getMessageText(), "Subject" , null ,cldr);
                }
                if(maingui.getSendTyp() == 4){
                maingui.getMessageQueueManager().createPrint(con, maingui.getMessageText(), "Subject" , null ,cldr);
                }

            }
    }
}
