package multichannel.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;

/**
 *
 * Liste für die Empfänger.
 * 
 * @author Stephan
 */
public class JListRecipient extends JList {

    JList recipientlist;
    DefaultListModel defaultListModel;

    /**
     * Generates a List with a Scrollbar.
     * 
     * Needs a defaultListModel who handels all Contacts.
     * 
     * @param defaultListModel
     */
    public JListRecipient(final DefaultListModel defaultListModel) {

        this.defaultListModel = defaultListModel;

        //Empfängerliste
        setModel(defaultListModel);
        setToolTipText("Doppelklick um Empfänger zu entfernen");
        
        //Tooltips sofort anzeigen ohne verzögerung
        ToolTipManager.sharedInstance().setInitialDelay(0);

        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // grösse definieren
        setSize(new Dimension( 100, 100 ));
        

        // 5 Kontakte Anzeigen
        setVisibleRowCount(5); 

        
        // MouseListener hinzufügen
        // Doppelklick entfernt kontakt
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    if( locationToIndex(getLocation()) >=0 ){
                        defaultListModel.remove(locationToIndex(evt.getPoint()));
                    }
                }
            }
        };
        addMouseListener(mouseListener);


    }
}
