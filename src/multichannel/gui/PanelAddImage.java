package multichannel.gui;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan
 * 
 * Generates the Panel for the Add-Image sektion
 */
public class PanelAddImage extends JPanel {
    
    JLabel labelpicturepath, labelpicture;
    JButton buttonfilechooser;
    
    /**
     * Generates Panel.
     * 
     * Needs the maingui for the Methods in the mainframe.
     * 
     * @param maingui
     */
    public PanelAddImage(GuiStart maingui){

        labelpicture = new JLabel("  Bild");
        labelpicturepath = new JLabel();

        buttonfilechooser = new ButtonFileChooser(this, maingui); // Create a

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(labelpicture);
        add(buttonfilechooser);
        add(labelpicturepath);

    }
    
    /**
     * Set the Text of the Path to the Picture.
     * 
     * @param path
     */
    public void setPP(String path){
        labelpicturepath.setText(path);
    }
    
    /**
     * Returns the path to the Picture
     * @return
     */
    public String getPP(){
        return labelpicturepath.getText();
    }
    
    /**
     * Set Visibility of the compinents.
     * a SMS needs no pictures.
     * 
     * @param truefalse
     */
    public void setVisibility(boolean truefalse){
        buttonfilechooser.setVisible(truefalse);
        labelpicture.setVisible(truefalse);
        labelpicturepath.setVisible(truefalse);
    }
   
}
