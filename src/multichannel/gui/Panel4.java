/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Stephan
 */
public class Panel4 extends JPanel {

    JTextArea txtareamessage;
    JLabel labelmessage, labelmessageerr;
    
    public Panel4() {

        // Konfig der Nachricht Abschnitt
        // Nachrichten Abschnitt (Scrollbalken, etc)
        labelmessage = new JLabel("Nachricht");
        labelmessageerr = new JLabel(); // Für Rückmeldungen
        
        txtareamessage = new JTextArea(20, 40);
        txtareamessage.setLineWrap(true);

        JScrollPane msgscroller = new JScrollPane(txtareamessage);
        msgscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        msgscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        txtareamessage.requestFocus();


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(labelmessage);
        add(labelmessageerr);
        add(txtareamessage);


    }
    
    public String getMessage(){
        return txtareamessage.getText();
        
    }
}
