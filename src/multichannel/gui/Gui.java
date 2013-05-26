package multichannel.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import multichannel.business.Contact;
import multichannel.business.MessageQueueManager;

public class Gui {

    // Hauptframe
    private final JFrame frame = new JFrame();
    // übergreifende variabel
    private String inputVerzStr;
    private JLabel labelpicturepath = new JLabel();
    private int sendtyp = 1; // Standardmässig E-Mail, wie Radio Button auswahl
    JLabel labelmessageerr = new JLabel(); // Für Rückmeldungen
    JTextArea txtareamessage = new JTextArea(20, 40);
    JComboBox timeList;
    //Empfänger Label als Array
    ArrayList<JLabel> labelreceiver = new ArrayList<JLabel>();
    JLabel textsendtime = new JLabel("sofort");
    JLabel labelsendtime = new JLabel("Sendezeit: ");
    JButton buttonsendtime = new JButton("Datum");
    JLabel labelminutes = new JLabel(" Minuten vorher");
    Calendar sendtime = Calendar.getInstance();
    JPanel pa_receiverlist = new JPanel();
    //leeren empfänger hinzufügen
    JLabel labelnoreceivers = new JLabel("noch keine Empfänger");
    // Statischer Absender
    Contact sender = new Contact("MultiChannel Client", "0000000000",
            "noreplay@test.ch", null);
    ArrayList<Contact> contactlist = new ArrayList<Contact>();
    // INSTANZ Manager
    private MessageQueueManager queueManager;

    /*
     * Set the Picture-Path (String needed)
     */
    public void setInputVerzStr(String input) {
        this.inputVerzStr = input;
    }

    /*
     * Returns the Picture-Path as a String
     */
    public String getInputVerzStr() {
        return this.inputVerzStr;
    }

    public Gui(MessageQueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void creategui() {

        // Frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center-Panel
        JPanel pa_center = new JPanel();

        // Empfänger-Panel
        JPanel pa_receiver = new JPanel();
        pa_receiver.setPreferredSize(new Dimension(0, 150));


        // Typ-Panel
        JPanel pa_typ = new JPanel();

        // SendeZeit-Panel
        JPanel pa_sendtime = new JPanel();
        textsendtime.setVisible(false);
        final JCheckBox checksendtime = new JCheckBox("Später senden?");
        checksendtime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checksendtime.isSelected() == true) {
                    textsendtime.setVisible(true);
                    labelsendtime.setVisible(true);
                    buttonsendtime.setVisible(true);

                    //Frame aktualisieren
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint();
                } else {
                    textsendtime.setVisible(false);
                    textsendtime.setText("sofort");
                    labelsendtime.setVisible(false);
                    buttonsendtime.setVisible(false);
                    //Frame aktualisieren
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint();
                }
            }
        });

        String[] times = {"15", "30", "45", "60", "120", "240"};

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        timeList = new JComboBox(times);
        timeList.setVisible(false);

        // Nachrichten-Pannel
        JPanel pa_message = new JPanel();
        JPanel pa_messageto = new JPanel();
        JPanel pa_messagemsg = new JPanel();

        // Bild-Hinzufügen-Pannel
        JPanel pa_addimage = new JPanel();

        /*
         * Generierung Menü-Leiste
         */
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuaction = new JMenu("Aktion");
        JMenu menuhelp = new JMenu("Hilfe");
        menuBar.add(menuaction);
        menuBar.add(menuhelp);

        // Aktions-Menü erstellen
        JMenuItem itemactqueue = new JMenuItem("Warteschlange ansehen");
        itemactqueue.setAccelerator(KeyStroke.getKeyStroke('W',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactqueue);
        itemactqueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "Sie möchte die aktuelle Nachrichten Warteschlange ansehen? \n\n"
                        + "Kein Problem! Dies ist in der Vollversion ohne Einschränkungen möglich..",
                        "Nachrichten Warteschlange",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        menuaction.addSeparator(); // Separator

        JMenuItem itemactexit = new JMenuItem("Exit");
        itemactexit.setAccelerator(KeyStroke.getKeyStroke('E',
                InputEvent.CTRL_MASK));
        menuaction.add(itemactexit);
        itemactexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Hilfe-Menü erstellen
        JMenuItem itemhelpabout = new JMenuItem("Über");
        itemhelpabout.setAccelerator(KeyStroke.getKeyStroke('U',
                InputEvent.CTRL_MASK));
        menuhelp.add(itemhelpabout);
        itemhelpabout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame msgframe = new JFrame();
                Object[] options = {"Super AWESOME!!"};
                JOptionPane
                        .showOptionDialog(
                        msgframe,
                        "Dieses Java-Programm wurde von Leandra Finger und Stephan Zumbühl\n"
                        + "an der ZHAW im Rahmen vom Programmieren-Projekt erstellt. \n\nDie Aufgabe war"
                        + "ein Multichannel zu erstellen wo über mehrere Medien versendet werden kann.\n\n",
                        "Information", JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.INFORMATION_MESSAGE, null, options,
                        null);

            }
        });






        // Empfänger Abschnitt
        JButton buttonsend = new JButton("Senden");
        buttonsend.setPreferredSize(new Dimension(80, 50));


        JButton buttonaddcontact = new JButton("Empfänger hinzufügen");
        buttonaddcontact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // KONTAKT erstellen und gleich hizufügen
                // (String name, String phone, String email, Printer printer)
                ArrayList<String> data = new ArrayList<String>();
                data = guicreatecontact();

                Contact tempcontact = new Contact(data.get(0), data.get(1),
                        data.get(2), null);
                contactlist.add(tempcontact);

                // Label
                if (data.get(0).length() > 0) {

                    final int stelle = labelreceiver.size();
                    labelreceiver.add(new JLabel(""));
                    pa_receiverlist.add(labelreceiver.get(stelle));
                    labelreceiver.get(stelle).setText(tempcontact.getName());
                    labelreceiver.get(stelle).setFont(new Font("Courier New", Font.PLAIN, 12));
                    labelreceiver.get(stelle).setBorder(BorderFactory.createRaisedBevelBorder());
                    // JList
                    //JList jList = new JList(arg0)
                    //http://java-tutorial.org/listmodel.html
                    labelreceiver.get(stelle).addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            labelreceiver.get(stelle).setBackground(Color.green);
                            labelreceiver.get(stelle).setToolTipText("E-Mail: " + contactlist.get(stelle).getEmail()
                                    + "   Telefon: " + contactlist.get(stelle).getPhone()
                                    + "  => Klicken um Kontakt zu entfernen");
                            //Tooltips sofort anzeigen ohne verzögerung
                            ToolTipManager.sharedInstance().setInitialDelay(0);
                        }

                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            if (labelreceiver.size() > 0) {
                                labelreceiver.get(stelle).setBackground(Color.LIGHT_GRAY);
                            }
                        }

                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            System.out.println("Kontakt Nummer gelöscht: " + stelle);

                            pa_receiverlist.remove(labelreceiver.get(stelle));
                            labelreceiver.remove(stelle);

                            if (labelreceiver.size() == 0) {
                                pa_receiverlist.add(labelnoreceivers);
                            }

                            //Frame aktualisieren wegen löschung
                            frame.getContentPane().validate();
                            frame.getContentPane().repaint();
                        }
                    });

                    // Liste der Empfänger dynamisch
                    if (labelreceiver.size() > 0) {
                        pa_receiverlist.remove(labelnoreceivers);
                        //	pa_receiverlist.add(labelreceiver.get(0));
                    } else {
                        pa_receiverlist.add(labelnoreceivers);
                    }

                    frame.getContentPane().validate();
                    frame.getContentPane().repaint();

                }
            }

            public ArrayList<String> guicreatecontact() {

                // Erstellung Array vom Datentyp Object, Hinzufügen der
                // Komponenten
                ArrayList<String> data = new ArrayList<String>();
                JTextField name = new JTextField();
                JTextField phone = new JTextField();
                JTextField email = new JTextField();
                JTextField printer = new JTextField();

                Object[] message = {"Name", name, "Telefon", phone, "E-Mail",
                    email, "Drucker", printer};

                JOptionPane pane = new JOptionPane(message,
                        JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                pane.createDialog(null, "Neuer Kontakt").setVisible(true);

                data.add(name.getText());
                data.add(phone.getText());
                data.add(email.getText());
                data.add(printer.getText());

                return data;
            }
        });

        buttonsend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // MAIL
                if (sendtyp == 1) {
                    JOptionPane.showMessageDialog(frame,
                            "E-MAIL...hulaaahuuua. Ich möchte noch definiert werden ",
                            "Ich bin ein nichts.",
                            JOptionPane.WARNING_MESSAGE);
                }

                // MMS
                if (sendtyp == 2) {
                    JOptionPane.showMessageDialog(frame,
                            "MMS....hulaaahuuua. Ich möchte noch definiert werden ",
                            "Ich bin ein nichts.",
                            JOptionPane.WARNING_MESSAGE);
                }

                // SMS
                if (sendtyp == 3) {

                    // Fehlerüberprüfung
                    // Check: Anzahl Zeichen
                    // Check: Empfänger
                    System.out.println("SIZE" + contactlist.size());
                    if (txtareamessage.getText().length() > 160
                            || contactlist.size() == 0) {
                        if (txtareamessage.getText().length() > 160) {
                            JOptionPane.showMessageDialog(frame,
                                    "Ein SMS darf nicht mehr als 160 Zeichen haben! \n "
                                    + "Bitte entferne "
                                    + (txtareamessage.getText()
                                    .length() - 160)
                                    + " zeichen.",
                                    "Mehr als 160 Zeichen",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                        if (contactlist.size() == 0) {
                            JOptionPane.showMessageDialog(frame,
                                    "Bitte füge einen Empfänger hinzu \n",
                                    "Keine Empfänger",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } else {

                        Calendar cldr = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "dd-MM-yyyy");
                        try {
                            cldr.setTime(sdf.parse(textsendtime.getText()));
                        } catch (ParseException e1) {
                            System.out.println(e1.getMessage());
                        }

                        // Umwandeln der contactlist ins das Format Collection
                        Collection<Contact> con = contactlist;

                        System.out.println("SMS WIRD Erstellt. Folgendes wird übergeben (DEBUG):");
                        System.out.println("Kontakte: " + con);
                        System.out.println("Nachricht: " + txtareamessage.getText());
                        System.out.println("Datum: " + cldr);

                        // Hier wird das SMS erstellt im QueeManager
                        // createSMS(Collection<Contact> recipients, String
                        // text, Calendar sendTime)
                        queueManager.createSMS(con, txtareamessage.getText(), cldr);

                    }

                }
                // PRINT
                if (sendtyp == 4) {
                    JOptionPane.showMessageDialog(frame,
                            "Drucker...hulaaahuuua. Ich möchte noch definiert werden ",
                            "Ich bin ein nichts.",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JLabel labelto = new JLabel("Empfänger: ");
        JLabel labeltoerr = new JLabel(); // Für Rückmeldungen

        labelnoreceivers.setFont(new Font("Courier New", Font.ITALIC, 12));

        // Konfig der Nachricht Abschnitt
        JLabel labeltyp = new JLabel("Typ: ");
        labelsendtime.setVisible(false);

        buttonsendtime.setVisible(false);
        buttonsendtime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // DATUM via neuem GUI einfügen
                sendtime = new Datepicker(frame).setPickedDate();

                // Zeit ins Feld schreiben
                // Vorher umwandeln
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                if (sendtime != null) {
                    textsendtime.setText(sdf.format(sendtime.getTime()));
                }
            }
        });

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
        rbuttonmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendtyp = 1;
                labelmessageerr.setText("");

            }
        });
        // MMS
        rbuttonmms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendtyp = 2;
                labelmessageerr.setText("");

            }
        });
        // SMS
        rbuttonsms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendtyp = 3;
                labelmessageerr.setText("Warnung: Max. 160 Zeichen!");
                labelmessageerr.setForeground(Color.red);
                labelmessageerr
                        .setFont(new Font("Courier New", Font.ITALIC, 12));

            }
        });
        // PRINT
        rbuttonprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendtyp = 4;
                labelmessageerr.setText("");

            }
        });

        // Nachrichten Abschnitt (Scrollbalken, etc)
        JLabel labelmessage = new JLabel("Nachricht");

        txtareamessage.setLineWrap(true);
        JScrollPane msgscroller = new JScrollPane(txtareamessage);
        msgscroller
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        msgscroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        txtareamessage.requestFocus();

        // Bild-Hinzufügen Abschnitt
        JLabel labelpicture = new JLabel("Bild");
        // labelpicturepath ist ausserhalb definiert aufgrund der inner-class
        JLabel labelpictureerr = new JLabel(); // Für Fehler-Rückmeldungen

        JButton filechooserpicture = new JButton("Bild auswählen"); // Create a
        // file
        // chooser
        // int returnVal = filechooserpicture.showOpenDialog(aComponent); //In
        // response to a button click:

        filechooserpicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle open button action.
                // give back the path as a STRING
                setInputVerzStr(filepicerframe());

                labelpicturepath.setText(getInputVerzStr());

            }

            public String filepicerframe() {
                final JFileChooser chooser = new JFileChooser(
                        "Verzeichnis wählen");
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                final File file = new File("/home");

                chooser.setCurrentDirectory(file);

                chooser.addPropertyChangeListener(new PropertyChangeListener() {
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
        });

        // GUI Zusammensetzen
        frame.getContentPane().add(BorderLayout.CENTER, pa_center);

        pa_center.setLayout(new BoxLayout(pa_center, BoxLayout.Y_AXIS));
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(pa_receiver);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(pa_typ);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(pa_sendtime);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(pa_message);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));
        pa_center.add(pa_addimage);
        pa_center.add(Box.createRigidArea(new Dimension(0, 10)));

        pa_receiver.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_receiver.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_receiver.add(buttonsend);
        pa_receiver.add(Box.createRigidArea(new Dimension(25, 0)));
        pa_receiver.add(buttonaddcontact);
        pa_receiver.add(labelto);
        pa_receiver.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_receiver.add(pa_receiverlist);
        pa_receiverlist.setLayout(new BoxLayout(pa_receiverlist, BoxLayout.Y_AXIS));

        // Liste initial = keine empfänger
        pa_receiverlist.add(labelnoreceivers);


        pa_receiver.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_receiver.add(labeltoerr);

        //pa_typ.setLayout(new BoxLayout(pa_typ, BoxLayout.X_AXIS));
        pa_typ.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_typ.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_typ.add(labeltyp);
        pa_typ.add(rbuttonmail);
        pa_typ.add(rbuttonmms);
        pa_typ.add(rbuttonsms);
        pa_typ.add(rbuttonprint);
        pa_typ.add(Box.createRigidArea(new Dimension(50, 0)));

        pa_sendtime.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_sendtime.add(checksendtime);
        pa_sendtime.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_sendtime.add(labelsendtime);
        pa_sendtime.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_sendtime.add(textsendtime);
        pa_sendtime.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_sendtime.add(buttonsendtime);
        pa_sendtime.add(Box.createRigidArea(new Dimension(5, 0)));

        pa_message.setLayout(new BoxLayout(pa_message, BoxLayout.Y_AXIS));
        pa_message.add(pa_messageto);
        pa_message.add(pa_messagemsg);
        pa_messagemsg.setLayout(new BoxLayout(pa_messagemsg, BoxLayout.X_AXIS));
        pa_messageto.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_messageto.add(labelmessage);
        pa_messageto.add(labelmessageerr);
        pa_messagemsg.add(Box.createRigidArea(new Dimension(10, 0)));
        pa_messagemsg.add(msgscroller);
        pa_messagemsg.add(Box.createRigidArea(new Dimension(10, 0)));

        pa_addimage.setLayout(new FlowLayout(FlowLayout.LEFT));
        pa_addimage.add(Box.createRigidArea(new Dimension(5, 0)));
        pa_addimage.add(labelpicture);
        pa_addimage.add(Box.createRigidArea(new Dimension(10, 0)));
        pa_addimage.add(filechooserpicture);
        pa_addimage.add(labelpicturepath);
        pa_addimage.add(labelpictureerr);

        // Frame anpassen und sichtbar machen
        frame.getContentPane().setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);

    }
}
