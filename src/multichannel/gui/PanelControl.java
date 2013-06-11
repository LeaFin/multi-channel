package multichannel.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import multichannel.business.Contact;

/**
 *
 * Generates the control-panel with the Send-Button and the whole receptions handling.
 * Typ: JPanel
 * 
 * @author Stephan
 */
public class PanelControl extends JPanel {

    DefaultListModel defaultListModel;
    

    /**
     * Generates the panel with all buttons etc.
     * 
     * Needs the maingui for the Methods in the mainframe.
     * 
     * @param maingui
     */
    public PanelControl(GuiStart maingui) {
        
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
        JScrollPane scroller = new JScrollPane(recipientlist);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       
        JButton buttonsend = new ButtonSend(this, maingui);
        JButton buttonadd = new ButtonAddContact(this, maingui);
        JButton buttonsearch = new ButtonSearchContact(this, maingui);

        JLabel labelto = new JLabel("Empfänger: ");
        JLabel labeltoerr = new JLabel(); // Für Rückmeldungen
        
        
        panelbuttonsend.add(buttonsend);
        panelbuttonsend.add(Box.createRigidArea(new Dimension(0, 100)));
        
        panelreciever.setLayout(new BoxLayout(panelreciever, BoxLayout.PAGE_AXIS));
        panelreciever.add(labelto);
        panelreciever.add(scroller);
        panelreciever.add(labeltoerr);
                
        pannelbuttonrcv.setLayout(new BoxLayout(pannelbuttonrcv, BoxLayout.PAGE_AXIS));
        pannelbuttonrcv.add(buttonadd);
        pannelbuttonrcv.add(Box.createRigidArea(new Dimension(10,10)));
        pannelbuttonrcv.add(buttonsearch);
        
        
    }

    
    
    
    /**
     * Adds a new contact in the datalist for the JList contact-List.
     * 
     * @param newcontact
     */
    public void addContact(Contact newcontact) {
        defaultListModel.addElement(newcontact);
    }

    /**
     * Returns the datalist for the JList contact-List.
     * @return defaultListModel for JLIst
     */
    public DefaultListModel getContactList() {
        return defaultListModel;
    }

}
