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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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

        final JFileChooser fileChooser = new JFileChooser("Verzeichnis wählen");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        final File file = new File("/home");

        fileChooser.setCurrentDirectory(file);

        // Alle Dateien ausblenden
        fileChooser.setAcceptAllFileFilterUsed(false);
        // Dateifilter setzen auf nur Bilder
        FileFilter filter = new FileNameExtensionFilter("Bilder", "jpg", "jpeg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);
 
        fileChooser.addPropertyChangeListener(new PropertyChangeListener() {
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

        fileChooser.setVisible(true);
        final int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File inputVerzFile = fileChooser.getSelectedFile();
            String inputVerzStr = inputVerzFile.getPath();
            // System.out.println("Eingabepfad:" + inputVerzStr);
            return inputVerzStr;
        }
        fileChooser.setVisible(false);
        return "";
    }
}

