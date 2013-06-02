/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author Stephan
 */
public class PanelTextforMessage extends JPanel {

    JTextField txtsubject;
    JTextArea txtareamessage;
    JLabel labelmessage, labelmessageinfo;

    public PanelTextforMessage() {

        // Panel worin alle Elemente kommen, für den Abstand auf den Seiten
        JPanel container = new JPanel();

        // Konfig der Nachricht Abschnitt
        // Nachrichten Abschnitt (Scrollbalken, etc)
        labelmessage = new JLabel("Nachricht");
        labelmessageinfo = new JLabel(); // Für Rückmeldungen

        txtsubject = new JTextField("Kein Betreff");

        txtareamessage = new JTextArea(20, 40);
        txtareamessage.setText("Nachricht eingeben");
       // txtareamessage.setBorder(new LineBorder(Color.BLACK, 1));
        txtareamessage.setLineWrap(true);
        

        JScrollPane msgscroller = new JScrollPane(txtareamessage);
        msgscroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        msgscroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(container);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(labelmessage);
        container.add(labelmessageinfo);
        container.add(txtsubject);
        container.add(Box.createRigidArea(new Dimension(7, 7)));
        container.add(msgscroller);
        add(Box.createRigidArea(new Dimension(10, 0)));

    }

    public String getMessage() {
        return txtareamessage.getText();
    }

    public String getSubject() {
        return txtsubject.getText();
    }

    public void setMessageInfo(String text) {
        labelmessageinfo.setText(text);
    }

    public void setVisibility(boolean truefalse) {
        txtsubject.setVisible(truefalse);
    }
}
