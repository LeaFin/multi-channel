/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import multichannel.business.Contact;

/**
 *
 * @author Stephan
 */
public class Panel1 extends JPanel {

    DefaultListModel defaultListModel;
    

    public Panel1(Gui maingui) {
        
        JPanel panelbuttonsend = new JPanel();
        JPanel panelreciever = new JPanel();
        JPanel pannelbuttonrcv = new JPanel();
        
        add(panelbuttonsend);
        add(panelreciever);
        add(pannelbuttonrcv);
        
        // Grösse und FlowLayout setzen vom Panel
        setMinimumSize(new Dimension( 1000, 130 ));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //Empfängerliste
        defaultListModel = new DefaultListModel();
        JListRecipient recipientlist = new JListRecipient(defaultListModel);

       
        JButton buttonsend = new ButtonSend(this, maingui);
        JButton buttonadd = new ButtonAddContact(this);
        JButton buttonsearch = new ButtonSearchContact(this);

        JLabel labelto = new JLabel("Empfänger: ");
        JLabel labeltoerr = new JLabel(); // Für Rückmeldungen
        
        
        panelbuttonsend.add(buttonsend);
        panelbuttonsend.add(Box.createRigidArea(new Dimension(0, 100)));
        
        panelreciever.setLayout(new BoxLayout(panelreciever, BoxLayout.PAGE_AXIS));
        panelreciever.add(labelto);
        panelreciever.add(new JScrollPane(recipientlist));
        panelreciever.add(labeltoerr);
                
        pannelbuttonrcv.setLayout(new BoxLayout(pannelbuttonrcv, BoxLayout.PAGE_AXIS));
        pannelbuttonrcv.add(buttonadd);
        pannelbuttonrcv.add(Box.createRigidArea(new Dimension(10,10)));
        pannelbuttonrcv.add(buttonsearch);
        
    }

    
    
    
    public void addContact(Contact newcontact) {
        defaultListModel.addElement(newcontact);
    }

    public DefaultListModel getContactList() {
        return defaultListModel;
    }
    
    
}
