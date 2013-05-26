/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import multichannel.business.Contact;

/**
 *
 * @author Stephan
 */
public class ButtonSend extends JButton implements ActionListener {

    Gui maingui;
    Panel1 panel1;

    public ButtonSend(Panel1 panel1, Gui maingui) {

        this.maingui = maingui;
        this.panel1 = panel1;


        setText("Senden");
        setPreferredSize(new Dimension(80, 50));

        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFrame msgframe = new JFrame();

        // MAIL

        if (maingui.getSendTyp() == 1) {
            System.out.println("Noch nicht definiert 1");
        }

        // MMS
        if (maingui.getSendTyp() == 2) {
            System.out.println("Noch nicht definiert 2");
        }

        // SMS
        if (maingui.getSendTyp() == 3) {

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

                Collection<Contact> con = null;

                // Umwandeln der contactlist ins das Format Collection
                   /*     for(int i = 0; i <= panel1.getContactList().getSize(); i++  ){
                 con = panel1.getContactList().get(i);
                 }*/

                System.out.println("SMS WIRD Erstellt. Folgendes wird übergeben (DEBUG):");
                System.out.println("Kontakte: " + con);
                System.out.println("Nachricht: " + maingui.getMessageText());
                System.out.println("Datum: " + cldr);

                // Hier wird das SMS erstellt im QueeManager
                maingui.getMessageQueueManager().createSMS(con, maingui.getMessageText(), cldr);

            }
        }


        // PRINT
        if (maingui.getSendTyp() == 4) {
            System.out.println("Noch nicht definiert 4");
        }


    }
}
