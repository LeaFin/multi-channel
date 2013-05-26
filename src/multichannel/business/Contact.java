/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel.business;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import multichannel.exception.NoContactException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandrafinger
 */
public class Contact implements Serializable {
    
    private String name;
    private String phone;
    private String email;
    private Printer printer;
    
    public Contact(String name, String phone, String email, Printer printer){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.printer = printer;
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
