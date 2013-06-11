package multichannel.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * The panel with subject and Message.
 * Typ: JPanel
 * 
 * @author Stephan
 */
public class PanelMessageTyp extends JPanel {

            
public PanelMessageTyp(GuiStart maingui){
        
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
