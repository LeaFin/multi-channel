/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import multichannel.exception.NoContactException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandrafinger
 */
public class Contact {
    
    private String name;
    private String phone;
    private String email;
    private Printer printer;
    private static List instances = new ArrayList<Object>();
    
    public Contact(String name, String phone, String email, Printer printer){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.printer = printer;
    }
    
    public static void createNewContact(String name, String phone, String email, Printer printer){
        Contact newContact = new Contact(name, phone, email, printer);
        instances.add(newContact);
    }
    
    public static Contact getByName(String name) throws NoContactException{
        for (Object object: instances){
            Contact contact = (Contact)object;
            if (contact.getName().equals(name)){
                return contact;
            }
        }
        throw new NoContactException();
    }
    
    public static Contact getByEmail(String email) throws NoContactException{
        for (Object object: instances){
            Contact contact = (Contact)object;
            if (contact.getEmail().equals(email)){
                return contact;
            }
        }
        throw new NoContactException();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Printer getPrinter() {
        return printer;
    }
    
    @Override
    public String toString(){
        return "Contact: " + name;
    }
}
