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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import multichannel.business.Contact;
import multichannel.exception.NoContactException;

/**
 *
 * @author Stephan
 */
public class ButtonSearchContact extends JButton implements ActionListener {

    Panel1 panel1;

    public ButtonSearchContact(Panel1 panel1) {

        this.panel1 = panel1;

        setText("Adressbuch öffnen");

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // KONTAKT erstellen und gleich hizufügen
            // (String name, String phone, String email, Printer printer) 
            panel1.addContact(searchcontact());
            
        } catch (NoContactException ex) {
            System.out.println("Fehler beim hinzufügen von dem neuen Kontakt!" + ex.getLocalizedMessage());
        }
    }

    public Contact searchcontact() throws NoContactException {
        
        // Frame für Meldungen
        JFrame msgframe = new JFrame();

        // Erstellung Array vom Datentyp Object, Hinzufügen der
        JTextField name = new JTextField();
        JTextField email = new JTextField();

        Object[] message = {"Name: ", name, "E-Mail: ", email, };

        JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "ADressbuch: Kontakt suchen").setVisible(true);

        // Kontakt suchen
        if( !name.getText().isEmpty() && !email.getText().isEmpty()){
            // Wenn Name gefunden und E-Mails ebenfalls eingeben, match??
            if( Contact.getByName(name.getText()).getEmail().equals(email.getText())){
                return Contact.getByName(name.getText());
            } else {
                JOptionPane.showMessageDialog(msgframe,
                            "Kontakt nicht gefunden. Bitte Name und E-Mail überprüfen.",
                            "Keine übereinstimmung",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if( !name.getText().isEmpty() ){
         try {
            return Contact.getByName(name.getText());
        } catch (NoContactException ex) {
            JOptionPane.showMessageDialog(msgframe,
                            "Keinen Kontakt mit dem Namen  '" + name.getText() + "' gefunden.",
                            "Keine übereinstimmung",
                            JOptionPane.ERROR_MESSAGE);
        }
            
        }
        
        if( !email.getText().isEmpty()){
        try {
            return Contact.getByEmail(email.getText());
        } catch (NoContactException ex) {
            JOptionPane.showMessageDialog(msgframe,
                            "Keinen Kontakt mit der E-Mail  '" + email.getText() + "' gefunden.",
                            "Keine übereinstimmung",
                            JOptionPane.ERROR_MESSAGE);
        }
            
        }
        
        return null;
        
    }
}
