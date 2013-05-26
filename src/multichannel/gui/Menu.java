/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import multichannel.business.Contact;

/**
 *
 * @author Stephan
 */
public class Menu extends JMenuBar implements ActionListener {

    GuiStart maingui;

    public Menu(GuiStart maingui) {

        this.maingui = maingui;

        JMenu menuaction = new JMenu("Aktion");
        JMenu menuhelp = new JMenu("Hilfe");
        add(menuaction);
        add(menuhelp);


        // Aktions-Menü erstellen
        JMenuItem itemactqueue = new JMenuItem("Warteschlange ansehen");
        itemactqueue.setAccelerator(KeyStroke.getKeyStroke('W',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactqueue);
        itemactqueue.addActionListener(this);

        menuaction.addSeparator(); // Separator

        JMenuItem itemactsavecon = new JMenuItem("Kontake speichern");
        itemactsavecon.setAccelerator(KeyStroke.getKeyStroke('S',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactsavecon);
        itemactsavecon.addActionListener(this);
        
        
        JMenuItem itemactshowcon = new JMenuItem("Kontake anzeigen");
        itemactshowcon.setAccelerator(KeyStroke.getKeyStroke('A',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactshowcon);
        itemactshowcon.addActionListener(this);

        menuaction.addSeparator(); // Separator

        JMenuItem itemactexit = new JMenuItem("Exit");
        itemactexit.setAccelerator(KeyStroke.getKeyStroke('E',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactexit);
        itemactexit.addActionListener(this);


        JMenuItem itemhelpabout = new JMenuItem("Über");
        itemhelpabout.setAccelerator(KeyStroke.getKeyStroke('U',
                InputEvent.CTRL_MASK));
        menuhelp.add(itemhelpabout);
        itemhelpabout.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        String command = evt.getActionCommand();

        if (command.equals("Warteschlange ansehen")) {
            JFrame msgframe = new JFrame();
            JOptionPane.showMessageDialog(msgframe,
                    "Sie möchte die aktuelle Nachrichten Warteschlange ansehen? \n\n"
                    + "Kein Problem! Dies ist in der Vollversion ohne Einschränkungen möglich..",
                    "Nachrichten Warteschlange",
                    JOptionPane.WARNING_MESSAGE);
        }


        if (command.equals("Kontake anzeigen")) {
            
            JFrame msgframe = new JFrame();
            
            JOptionPane.showMessageDialog(msgframe,
                    "Das sind alle Kontakte im Speicher:\n" + maingui.getContactList().getContacts(),
                    "Kontakte im speicher",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (command.equals("Kontake speichern")) {

            maingui.getContactList().serializeContacts();

            JFrame msgframe = new JFrame();
            JOptionPane.showMessageDialog(msgframe,
                    "Kontakte wurden lokal abgespeichert!",
                    "Kontakte gespeichert",
                    JOptionPane.INFORMATION_MESSAGE);
        }


        if (command.equals("Exit")) {
            System.exit(0);
        }


        if (command.equals("Über")) {
            JFrame msgframe = new JFrame();
            Object[] options = {"Super AWESOME!!"};
            JOptionPane
                    .showOptionDialog(msgframe,
                    "Dieses Java-Programm wurde von Leandra Finger und Stephan Zumbühl entwickelt.\n"
                    + "Es wurde an der ZHAW im Rahmen vom Programmieren-Projekt erstellt. \n\n"
                    + "Die Aufgabe war ein 'Multichannel' zu erstellen, welcher über mehrere Nachrichten-Medien versenden kann.\n\n",
                    "Information", JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.INFORMATION_MESSAGE, null, options,
                    null);

        }
    }
}
