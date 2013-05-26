/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import multichannel.business.Contact;

/**
 *
 * @author Stephan
 */
public class Panel1 extends JPanel {

    DefaultListModel defaultListModel;
    JList recivlist;

    public Panel1(Gui maingui) {
        
        // Grösse und FlowLayout setzen vom Panel
        setMinimumSize(new Dimension( 1000, 100 ));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        
        
        //Empfängerliste
        defaultListModel = new DefaultListModel();
        recivlist = new JList(defaultListModel);

        recivlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        recivlist.setSize(900, 100);
        recivlist.setVisibleRowCount(5); // 5 Kontakte Anzeigen


        JButton buttonsend = new ButtonSend(this, maingui);
        JButton buttonadd = new ButtonAddContact(this);

        JLabel labelto = new JLabel("Empfänger: ");
        JLabel labeltoerr = new JLabel(); // Für Rückmeldungen
        
        add(buttonsend);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(labelto);
        add(buttonadd);
        add(labeltoerr);
        add(new JScrollPane(recivlist));
    }

    
    
    
    public void addContact(Contact newcontact) {
        defaultListModel.addElement(newcontact);
    }

    public DefaultListModel getContactList() {
        return defaultListModel;
    }
    
    
}
