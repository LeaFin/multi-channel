package multichannel.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author Stephan
 * 
 * Adds a checkbox with the 4 Message-Typs Mail, MMS, SMS, Printer
 * 
 * Set the choosen Typ direkt in the Maingui.
 * 
 */
public class ButtonSendTyp extends JPanel implements ActionListener {

    GuiStart maingui;
            
    /**
     * Generates the radiobuttons with the Sendtyps.
     * Needs the maingui to set the choosen type.
     * @param maingui
     */
    public ButtonSendTyp(GuiStart maingui) {

        this.maingui = maingui;

        
        // Create the radio buttons.
        JRadioButton rbuttonmail = new JRadioButton("E-Mail");
        rbuttonmail.setSelected(true);

        JRadioButton rbuttonmms = new JRadioButton("MMS");
        rbuttonmms.setSelected(false);

        JRadioButton rbuttonsms = new JRadioButton("SMS");
        rbuttonsms.setSelected(false);

        JRadioButton rbuttonprint = new JRadioButton("Drucken");
        rbuttonprint.setSelected(false);

        // Group the radio buttons.
        ButtonGroup radiobuttongroup = new ButtonGroup();
        radiobuttongroup.add(rbuttonmail);
        radiobuttongroup.add(rbuttonmms);
        radiobuttongroup.add(rbuttonsms);
        radiobuttongroup.add(rbuttonprint);

        // Register a listener for the radio buttons.
        // MAIL
        rbuttonmail.addActionListener(this);
        rbuttonmms.addActionListener(this);
        rbuttonsms.addActionListener(this);
        rbuttonprint.addActionListener(this);
        
        add(rbuttonmail);
        add(rbuttonmms);
        add(rbuttonsms);
        add(rbuttonprint);
        

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        
        JRadioButton radiobutton = (JRadioButton) evt.getSource();

        // E_MAIL
        if (radiobutton.getText().equals("E-Mail")) {
            maingui.setSendTyp(1);
            //labelmessageerr.setText("");
        }
        // MMS
        if (radiobutton.getText().equals("MMS")) {
            maingui.setSendTyp(3);
            //labelmessageerr.setText("");
        }
        // SMS
        if (radiobutton.getText().equals("SMS")) {
            maingui.setSendTyp(2);
            //labelmessageerr.setText("Warnung: Max. 160 Zeichen!");
            //labelmessageerr.setForeground(Color.red);
            //labelmessageerr.setFont(new Font("Courier New", Font.ITALIC, 12));
        }
        // PRINT
        if (radiobutton.getText().equals("Drucken")) {
            maingui.setSendTyp(4);

            //labelmessageerr.setText("");
        }
    }
}