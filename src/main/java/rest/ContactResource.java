/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.ContactDTO;
import dtos.OpportunityDTO;
import entities.Contact;
import errorhandling.MissingInputException;
import errorhandling.NotFoundException;
import facades.ContactFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 *
 * @author groen
 */
@Path("contact")
public class ContactResource {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private Gson gson = new Gson();
    private final ContactFacade contactFacade = ContactFacade.getContactFacade(EMF);
//    private final AdminFacade adminFacade = AdminFacade.getAdminFacade(EMF);

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-contact")
    //@RolesAllowed("user")
    public String addContact(String jsonString) throws MissingInputException {
        ContactDTO contactDTO;
        Contact contact;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String userName = json.get("userName").getAsString();
            String name = json.get("name").getAsString();
            String email = json.get("email").getAsString();
            String company = json.get("company").getAsString();
            String jobtitle = json.get("jobtitle").getAsString();
            String phone = json.get("phone").getAsString();            
            contact = new Contact(name, email, company, jobtitle, phone);
            ContactDTO cDTO = new ContactDTO(contact);
            contactFacade.addContact(userName, cDTO);
        } catch (MissingInputException | NullPointerException s) {
            return "{\"msg\":\"Missing contact information!\"}";
        }
        return "{\"msg\":\"Contact added\"}";
        
        
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get-contacts")
    //@RolesAllowed("user")
    public String getContacts (String jsonString) {
        List<ContactDTO> contacts = contactFacade.getAllContacts();
        return gson.toJson(contacts);
    }
    
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editContact(@PathParam("id") long id, String name) throws NotFoundException, MissingInputException {
        Contact contact = GSON.fromJson(name, Contact.class);
        contact.setId(id);
        ContactDTO pEdit = contactFacade.editContact(contact);
        return GSON.toJson(pEdit);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-opportunity")
    //@RolesAllowed("user")
    public String addOpportunity(String jsonString) throws MissingInputException {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            Long id = json.get("id").getAsLong();
            int amount = json.get("amount").getAsInt();
            String name = json.get("name").getAsString();
            String closeDate = json.get("closeDate").getAsString();
            OpportunityDTO oDTO = new OpportunityDTO(amount, name, closeDate);
            contactFacade.addOpportunityToContact(oDTO, id);
        } catch ( NullPointerException s) {
            return "{\"msg\":\"Missing contact information!\"}";
        }
        return "{\"msg\":\"Opportunity added\"}";
        
        
    }
    
    
//    @Path("{id}")
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    public String deleteContact(@PathParam("id") long id, String name) throws NotFoundException {
//        List
//    }
//    
    
}
