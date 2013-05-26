/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import multichannel.business.Contact;
import multichannel.exception.NoContactException;

/**
 *
 * @author Stephan
 */
public class ButtonAddContact extends JButton implements ActionListener {

    Panel1 panel1;

    public ButtonAddContact(Panel1 panel1) {

        this.panel1 = panel1;

        setText("Neuer Empfänger");

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // KONTAKT erstellen und gleich hizufügen
            // (String name, String phone, String email, Printer printer) 
            panel1.addContact(createcontact());
            
        } catch (NoContactException ex) {
            System.out.println("Fehler beim hinzufügen von dem neuen Kontakt!" + ex.getLocalizedMessage());
        }

    }

    public Contact createcontact() throws NoContactException {

        // Erstellung Array vom Datentyp Object, Hinzufügen der
        // Komponenten
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JTextField printer = new JTextField();

        Object[] message = {"Name", name, "Telefon", phone, "E-Mail",
            email, "Drucker", printer};

        JOptionPane pane = new JOptionPane(message,
                JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Neuer Kontakt").setVisible(true);

        // Neuer Kontakt in die Liste aufnehmen
        //printer.getText() fehtl
        Contact.createNewContact(name.getText(), phone.getText(), email.getText(), null);

        return Contact.getByName(name.getText());
    }
}
