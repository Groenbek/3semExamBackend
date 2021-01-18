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
import errorhandling.MissingInputException;
import facades.ContactFacade;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @RolesAllowed("user")
    public String addContact(String jsonString) throws MissingInputException {
        ContactDTO contactDTO;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String userName = json.get("userName").getAsString();
            String name = json.get("name").getAsString();
            String email = json.get("email").getAsString();
            String company = json.get("company").getAsString();
            String jobtitle = json.get("jobtitle").getAsString();
            String phone = json.get("phone").getAsString();
            contactDTO = new ContactDTO(null, "name", "email", "company", "jobtitle", "phone");
            contactFacade.addContact(userName, contactDTO);
        } catch (MissingInputException | NullPointerException s) {
            return "{\"msg\":\"Missing contact information!\"}";
        }
        return "{\"msg\":\"Contact added\"}";
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("get-dogs/{userName}")
//    @RolesAllowed("user")
//    public String getDogs(@PathParam("userName") String userName) {
//        List<DogDTO> dogs = dogFacade.getDogs(userName);
//        return gson.toJson(dogs);
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("get-dogs/breeds")
//    public String getDogs() throws IOException {
//        adminFacade.addSearch();
//        String breeds = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed");
//        JsonObject json = JsonParser.parseString(breeds).getAsJsonObject();
//        return GSON.toJson(json.getAsJsonArray("dogs"));
//    }

}
