/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.ContactDTO;
import entities.Contact;
import entities.Role;
import entities.User;
import errorhandling.MissingInputException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author groen
 */
public class ContactFacadeTest {
    
    private static EntityManagerFactory emf;
    private static ContactFacade facade;
    private User user;
    private User admin;
    private User both;

    public ContactFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ContactFacade.getContactFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Contact").executeUpdate(); 
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate(); 
            user = new User("user", "test1");
            admin = new User("admin", "test1");
            both = new User("user_admin", "test1");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            Contact Contact1 = new Contact("Hans Erik", "Hansemand@Mandsehans.dk", "Novo", "CEO", "23456789");
            Contact Contact2 = new Contact("Yvonne Stranning", "StranningYv@BullesBageri.dk", "Bulles Bageri", "Bager", "47295738");
            user.addContact(Contact1);
            admin.addContact(Contact2);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
    

    
    @Test
    public void testAddContact(){
        EntityManager em = emf.createEntityManager();
        Contact con1 = new Contact("Hans", "Hans@hans.dk", "ARLA", "CEO", "48293843");
        Contact con2 = new Contact("Jytte", "Jytte@hans.dk", "SONY", "CEO", "23456789");
        try {
            em.getTransaction().begin();
            em.persist(con1);
            em.persist(con2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        int expectedContacts = 4;
        assertEquals(expectedContacts, facade.getAllContacts().size(), "Expects 4 people");
    }
//    @Test
//    public void testGetContacts() {
//        int actualSize = facade.getAllContacts().size();
//        int expectedSize = 2;
//        assertEquals(expectedSize, actualSize, "Expects 2 contacts");
//    }
}