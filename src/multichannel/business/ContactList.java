/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import multichannel.exception.NoContactException;

/**
 *
 * @author leandrafinger
 */
public class ContactList implements Serializable {
    
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    
    public Contact createNewContact(String name, String phone, String email, Printer printer){
        Contact newContact = new Contact(name, phone, email, printer);
        contacts.add(newContact);
        return newContact;
    }
    
    public Contact getByName(String name) throws NoContactException{
        for (Contact contact: contacts){
            if (contact.getName().equals(name)){
                return contact;
            }
        }
        throw new NoContactException();
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    
    @Override
    public String toString(){
        String output = "";
        for (Contact contact : contacts){
            output += contact + ", ";
        }
        return output;
    }
    
    public Contact getByEmail(String email) throws NoContactException{
        for (Contact contact: contacts){
            if (contact.getEmail().equals(email)){
                return contact;
            }
        }
        throw new NoContactException();
    }
    
        public void serializeContacts(){
        try {
            FileOutputStream fileStream = new FileOutputStream("Contacts.ser");
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this);
            os.close();
        }
        catch (Exception e) {
            System.out.println("Contacts can't be saved.");
        }
    }
    
    public static ContactList deserializeContacts(){
        FileInputStream fileStream;
        ContactList obj = null;
        try {
            fileStream = new FileInputStream("Contacts.ser");
            ObjectInputStream is = new ObjectInputStream(fileStream);
            obj = (ContactList)is.readObject();
            is.close();
        }
        catch (FileNotFoundException fnf){
            return new ContactList();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}
