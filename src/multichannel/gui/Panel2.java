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
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Stephan
 */
public class Panel2 extends JPanel {

            
public Panel2(GuiStart maingui){
        
    // Label für "TYP :"
    JLabel labeltyp = new JLabel("Typ: ");
    
    // Rbuttons hinzufügen
    JPanel radiobutton = new ButtonSendTyp(maingui);
    
    // Layout des Panel setzen
    setLayout(new FlowLayout(FlowLayout.LEFT));
    
    
    
    // Komponenten zum Panel hinzufügen
    add(labeltyp);
    add(Box.createRigidArea(new Dimension(5, 0)));
    add(radiobutton);
}
    
    
    
}
