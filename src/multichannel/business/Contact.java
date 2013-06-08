/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import java.io.Serializable;

/**
 *
 * @author leandrafinger
 */
public class Contact implements Serializable {
    
    private String name;
    private String phone;
    private String email;
    private String printer;
    
    public Contact(String name, String phone, String email, String printer){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.printer = printer;
    }
    
    /**
     * setter for instance variable email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * setter for instance variable name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * setter for instance variable phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * setter for instance variable printer
     * @param printer
     */
    public void setPrinter(String printer) {
        this.printer = printer;
    }
    
    /**
     * getter for instance variable email
     * @return String email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * getter for instance variable name
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for instance variable phone
     * @return String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * getter for instance variable printer
     * @return String printer
     */
    public String getPrinter() {
        return printer;
    }
    
    @Override
    public String toString(){
        return "Contact: " + name;
    }
}
