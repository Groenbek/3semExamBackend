package facades;

import dtos.ContactDTO;
import dtos.OpportunityDTO;
//import dtos.OpportunityDTO;
//import entities.Opportunity;
import entities.Contact;
import entities.Opportunity;
import entities.User;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
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
     private EntityManager getEntityManager() {
        return emf.createEntityManager();
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
    
    public List<ContactDTO> getAllContacts() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Contact> query = em.createQuery("SELECT a FROM Contact a", Contact.class);
            List<Contact> contacts = query.getResultList();
            ArrayList<ContactDTO> ContactDTOList = new ArrayList<ContactDTO>();
            for(Contact c: contacts){
                ContactDTOList.add(new ContactDTO(c));
            }
            em.getTransaction().commit();
            return ContactDTOList;
        } finally {
            em.close();
        }
    }
    
        public ContactDTO editContact(Contact c) throws MissingInputException, NotFoundException {
        EntityManager em = getEntityManager();
        try {
            Contact contact = em.find(Contact.class, c.getId());
            if (c == null) {
                throw new NotFoundException("The chosen action is not possible");
            }
            em.getTransaction().begin();
            contact.setName(c.getName());
            contact.setEmail(c.getEmail());
            contact.setCompany(c.getCompany());
            contact.setJobtitle(c.getJobtitle());
            contact.setPhone(c.getPhone());

            em.getTransaction().commit();
            return new ContactDTO(contact);
        } finally {
            em.close();
        }
        }
            //Delete Contact by id
        public ContactDTO deleteContact(Long id) throws MissingInputException {
        EntityManager em = getEntityManager();
        try {
                    em.getTransaction().begin();
            Contact contact = em.find(Contact.class, id);
            if (contact == null) {
                throw new MissingInputException();
            }
    
            em.remove(contact);
            em.getTransaction().commit();
            return new ContactDTO(contact);
        } finally {
            em.close();
        }
    }

        public void addOpportunityToContact(OpportunityDTO opportunityDTO, Long id) throws MissingInputException{
         EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            
           Contact contact = em.find(Contact.class, id);
                Opportunity opportunity = new Opportunity(opportunityDTO.getAmount(), opportunityDTO.getName(), opportunityDTO.getCloseDate());
                contact.AddOpportunities(opportunity);
                em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}