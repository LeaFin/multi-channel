/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.gui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan
 */
public class PanelAddImage extends JPanel {
    
    JLabel labelpicturepath, labelpicture;
    JButton buttonfilechooser;
    
    public PanelAddImage(GuiStart maingui){

        labelpicture = new JLabel("  Bild");
        labelpicturepath = new JLabel();

        buttonfilechooser = new ButtonFileChooser(this, maingui); // Create a

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(labelpicture);
        add(buttonfilechooser);
        add(labelpicturepath);

    }
    
    public void setPP(String path){
        labelpicturepath.setText(path);
    }
    
    public String getPP(){
        return labelpicturepath.getText();
    }
    
    public void setVisibility(boolean truefalse){
        buttonfilechooser.setVisible(truefalse);
        labelpicture.setVisible(truefalse);
        labelpicturepath.setVisible(truefalse);
    }
   
}
