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
public class Printer implements Serializable {
    
    private String name;
    private String addresse;
    
    public Printer(String name, String addresse){
        this.name = name;
        this.addresse = addresse;
    }

    public String getAddresse() {
        return addresse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Printer: " + name;
    }
    
}
