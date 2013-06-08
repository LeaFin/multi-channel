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
import multichannel.exception.ContactInvalidException;

/**
 *
 * @author leandrafinger
 */
public class ContactList implements Serializable {
    
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    
    
    /**
     * Checks the new Contact. Minimum a Name with an Phonenumber, E-Mail Adress or Printer is needed.
     * There can't be two contacts with the same name.
     * 
     * @param name
     * @param phone
     * @param email
     * @param printer
     * @return true or false
     */
    public boolean validateContact(String name,String phone,String email,String printer){
        
        if(name.isEmpty() || (phone.isEmpty() && email.isEmpty() && printer.isEmpty())){
            System.out.println("You need to add at least one contact information and name");
            return false;
        }
        
        try{
            getByName(name);
            System.out.println("You can't have 2 contacts with the same name.");
            return false;
        }
        catch (NoContactException e){
            return true;
        }
    }
            
    /**
     * Calling the Contact constructor if parameters are valid and adding the new Contact to the list.
     * If input isn't valid thorws ContactInvalidException.
     * @param name
     * @param phone
     * @param email
     * @param printer
     * @return Contact
     * @throws ContactInvalidException
     */
    public Contact createNewContact(String name, String phone, String email, String printer) throws ContactInvalidException{
        boolean valid = validateContact(name, phone, email, printer);
        if (valid){
            Contact newContact = new Contact(name, phone, email, printer);
            contacts.add(newContact);
            return newContact;
        }
        else {
            throw new ContactInvalidException();
        }
    }
    
    /**
     * Returns the contact with fitting name, if none thorws an NoContactException.
     * @param name
     * @return
     * @throws NoContactException
     */
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
    
    /**
     * Returns the contact with fitting email, if none thorws an NoContactException.
     * @param email
     * @return
     * @throws NoContactException
     */
    public Contact getByEmail(String email) throws NoContactException{
        for (Contact contact: contacts){
            if (contact.getEmail().equals(email)){
                return contact;
            }
        }
        throw new NoContactException();
    }
    
    /**
     *  Serializes the contactlist to the file Contacts.ser.
     *  So with a new program start all contacts will be loaded again.
     */
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
    
    
    /**
     *  Deserializes the contactlist from file Contacts.ser.
     *  This method is called, when the program gets started.
     */
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
