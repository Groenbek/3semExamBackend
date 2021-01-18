package facades;

import dtos.ContactDTO;
import entities.Contact;
import entities.User;
import errorhandling.MissingInputException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author groen
 */
public class ContactFacade {
    

    private static EntityManagerFactory emf;
    private static ContactFacade instance;

    private ContactFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static ContactFacade getContactFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ContactFacade();
        }
        return instance;
    }
// String name, String email, String company, String jobtitle, int phone
    public void addContact(String userName, ContactDTO contactDTO) throws MissingInputException {
        EntityManager em = emf.createEntityManager();
        try {
            if (!(userName.isEmpty() || contactDTO.getName().isEmpty() || contactDTO.getEmail().isEmpty() || contactDTO.getCompany().isEmpty() || contactDTO.getJobtitle().isEmpty() || contactDTO.getPhone().isEmpty())) {
                em.getTransaction().begin();
                User user = em.find(User.class, userName);
                Contact contact = new Contact(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getCompany(), contactDTO.getJobtitle(), contactDTO.getPhone());
                user.addContact(contact);
                em.getTransaction().commit();
            } else {
                throw new MissingInputException();
            }

        } finally {
            em.close();
        }
    }
    public List<ContactDTO> getContacts(String userName) {
        List<ContactDTO> contacts = new ArrayList();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userName);
            TypedQuery<Contact> userQuery = em.createQuery("SELECT a FROM Contact a WHERE a.user.userName = :username", Contact.class);
            List<Contact> allContacts = userQuery.setParameter("username", userName).getResultList();
            for (Contact aContact : allContacts) {
                contacts.add(new ContactDTO(aContact.getId(), aContact.getName(), aContact.getEmail(), aContact.getCompany(), aContact.getJobtitle(), aContact.getPhone()));
            }
            em.getTransaction().commit();
            return contacts;
        } finally {
            em.close();
        }
    }
}