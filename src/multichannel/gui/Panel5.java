/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan
 */
public class Panel5 extends JPanel {
    
    JLabel labelpicturepath, labelpicture;
    
    public Panel5(Gui maingui){

        labelpicture = new JLabel("Bild");
        labelpicturepath = new JLabel(); // Für Fehler-Rückmeldungen

        JButton buttonfilechooser = new ButtonFileChooser(this, maingui); // Create a

        add(labelpicture);
        add(buttonfilechooser);
        add(labelpicturepath);

    }
    
    public void setPP(String path){
        labelpicturepath.setText(path);
    }
    
}
