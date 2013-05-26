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

/**
 *
 * @author Stephan
 */
public class ButtonFileChooser extends JButton implements ActionListener {

    GuiStart maingui;
    PanelAddImage panel5;

    public ButtonFileChooser(PanelAddImage panel5, GuiStart maingui) {

        this.maingui = maingui;
        this.panel5 = panel5;

        setText("Bild auswählen");

        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String path = filepicerframe();
        this.panel5.setPP(path);
        this.maingui.setPicturePath(path);

    }

    public String filepicerframe() {

        final JFileChooser chooser = new JFileChooser("Verzeichnis wählen");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        final File file = new File("/home");

        chooser.setCurrentDirectory(file);

        chooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals(
                        JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
                        || e.getPropertyName()
                        .equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
                    final File f = (File) e.getNewValue();
                }
            }
        });

        chooser.setVisible(true);
        final int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File inputVerzFile = chooser.getSelectedFile();
            String inputVerzStr = inputVerzFile.getPath();
            // System.out.println("Eingabepfad:" + inputVerzStr);
            return inputVerzStr;
        }
        chooser.setVisible(false);
        return "";
    }
}

