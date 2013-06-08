/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multichannel;

import multichannel.business.Contact;
import multichannel.business.ContactList;
import multichannel.exception.ContactInvalidException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stephan
 */
public class ContactListTest {

    static Contact testcontact1, testcontact2;
    static String name1, phone1, email1, printer1, name2, phone2, email2, printer2;
    static ContactList contacts;

    public ContactListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws ContactInvalidException {

        name1 = "Stephan";
        phone1 = "0774565252";
        email1 = "sz@zii.ch";
        printer1 = "\\\\Print03\\Printtest";

        name2 = "Leandra";
        phone2 = "0774565252";
        email2 = "leandra@finger.ch";
        printer2 = "\\\\Print05\\Printtestleandra";

        contacts = new ContactList();

        testcontact1 = contacts.createNewContact(name1, phone1, email1, printer1);
        testcontact2 = contacts.createNewContact(name2, phone2, email2, printer2);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test error when invalid contact gets created.
     * Contact with the same name already exists.
     */
    @Test (expected=ContactInvalidException.class)
    public void testContactValidationNameDuplication() throws ContactInvalidException {
        System.out.println("Test ContactValidation");
        contacts.createNewContact(name1, phone1, email1, printer1);
    }
    
    /**
     * Test error when invalid contact gets created.
     * Just name defined
     */
    @Test (expected=ContactInvalidException.class)
    public void testContactValidationNotEnoughInfo() throws ContactInvalidException {
        System.out.println("Test ContactValidation");
        contacts.createNewContact("NAME", "", "", "");
    }

    /**
     * Test of createNewContact method, of class ContactList.
     */
    @Test
    public void testCreateNewContact() throws ContactInvalidException {
        System.out.println("createNewContact");
        String name = "Stephan";
        String phone = "0774565252";
        String email = "sz@zii.ch";
        String printer = "\\\\Print03\\Printtest";

        ContactList instance = new ContactList();
        Contact testcontact = instance.createNewContact(name, phone, email, printer);

        String expResult = name;
        String result = testcontact.getName();
        assertEquals(expResult, result);

        expResult = phone;
        result = testcontact.getPhone();
        assertEquals(expResult, result);

        expResult = email;
        result = testcontact.getEmail();
        assertEquals(expResult, result);

        expResult = printer;
        result = testcontact.getPrinter();
        assertEquals(expResult, result);

    }

    /**
     * Test of getByName method, of class ContactList.
     */
    @Test
    public void testGetByName() throws Exception {
        System.out.println("getByName");
        String name = "Stephan";
        String phone = "0774565252";
        String email = "sz@zii.ch";
        String printer = "\\\\Print03\\Printtest";

        ContactList myContacts = new ContactList();

        Contact testcontact = myContacts.createNewContact(name, phone, email, printer);

        String expResult = name;
        String result = myContacts.getByName(name).getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getContacts method, of class ContactList.
     */
    @Test
    public void testGetContacts() {
        System.out.println("getContacts");

        int expResult = 2;
        int result = contacts.getContacts().size();

        assertEquals(expResult, result);

    }


}