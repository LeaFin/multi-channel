package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import multichannel.business.Contact;
import multichannel.exception.ContactInvalidException;
import multichannel.exception.NoContactException;


/**
 *
 * Adds Button "Neuer Empfänger"
 * New Contacts are direkt handled with the JList.
 * All other methods and Dialogs included in this class
 * Typ: JButton
 * 
 * @author Stephan
 * 
 */
public class ButtonAddContact extends JButton implements ActionListener {

    PanelControl panel1;
    GuiStart maingui;

    public ButtonAddContact(PanelControl panel1, GuiStart maingui) {

        this.panel1 = panel1;
        this.maingui = maingui;

        setText("Neuer Empfänger");

        addActionListener(this);
    }

     /**
     * 
     * Button hit = new OptionPane for a new contact.
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // KONTAKT erstellen und gleich hizufügen
            // (String name, String phone, String email, Printer printer) 
            Contact newContact = createcontact();
            if (newContact != null){
                panel1.addContact(newContact);
            }
            
        } catch (NoContactException ex) {
            System.out.println("Fehler beim hinzufügen von dem neuen Kontakt!" + ex.getLocalizedMessage());
        }

    }

    /**
     * Create a new Contact. A OptionPane appears if the Button hit.
     * 
     * 
     * @return Generated Contact
     * 
     * @throws NoContactException
     */
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

        try {
            maingui.getContactList().createNewContact(name.getText(), phone.getText(), email.getText(), printer.getText());
            return maingui.getContactList().getByName(name.getText());
        } catch (ContactInvalidException ex) {
            PopUpErrorContact();
            return null;
        }      
    }
    
     /**
     * Error Dialog
     */
    private void PopUpErrorContact() {
        JFrame msgframe = new JFrame();
        JOptionPane.showMessageDialog(msgframe,
                "Kontakt wurde nicht erstellt!\n"+
                "Ein Kontakt muss mindestens einen Namen sowie E-Mail, Nummer oder Drucker haben.\n"+
                "Ein Name darf nur einmal in der Kontaktliste existieren.",
                "Kontakt erstellen fehlgeschlagen",
                JOptionPane.WARNING_MESSAGE);
    }

}
