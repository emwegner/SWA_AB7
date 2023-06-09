package org.acme.boundary.rest;


import org.acme.Security.User;
import org.acme.boundary.rest.DTOs.BestellpunktDTO;
import org.acme.control.BestellpunktService;
import org.acme.control.BestellungService;

import org.acme.entity.Kunde;
import org.acme.entity.Pizza;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bestellungen")
public class BestellungResource {

    @Inject
    BestellungService bestellungService;

    @Inject
    BestellpunktService bestellpunktService;
 

    @Inject
    Template bestellungTemplate;
    @Inject
    Template errorTemplate;


        
    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance getBestellungen(@Context SecurityContext securityContext) {
         User user = User.findByName(securityContext.getUserPrincipal().getName());
         if(user.role.equals("user")) {
           Kunde kunde = Kunde.findById(user.kundenid);
           return bestellungTemplate.data("bestellungen",bestellpunktService.BestellungspunkteAbfragen(kunde)); 
         }
          if(user.role.equals("admin")) {
          return bestellungTemplate.data("bestellungen",bestellpunktService.BestellungspunkteAbfragen()); 
         }
       return errorTemplate.data("Error occured");

    }
    
    

   @POST
    @Transactional
    @Path("/add")
    @RolesAllowed({"admin", "user"})
    public Response addBestellpunkt(BestellpunktDTO dto, @Context SecurityContext securityContext) {
      User user = User.findByName(securityContext.getUserPrincipal().getName());
      if(user.role.equals("admin"))  return Response.notModified("not Valid: Admin kein Kunde").build();
      Kunde kunde = Kunde.findById(user.kundenid);

      if(dto != null) {
        Pizza pizza = Pizza.findById(dto.getPizzaid());
        bestellpunktService.BestellungspunktHinzufuegen(kunde,pizza,dto.getAmount());
        return Response.ok("Bestellung hinzugefuegt!").build();    
        } 
        return Response.notModified("not Valid: addBestellpunkt").build();

    }

    @DELETE
    @Transactional
    @Path("/delete")
    @RolesAllowed({"admin", "user"})
    public Response deleteBestellpunkt(BestellpunktDTO dto,@Context SecurityContext securityContext) {
       User user = User.findByName(securityContext.getUserPrincipal().getName());
       Kunde kunde = Kunde.findById(user.kundenid);
             if(dto != null) {
        Pizza pizza = Pizza.findById(dto.getPizzaid());
        bestellpunktService.BestellungspunktLoeschen(kunde,pizza,dto.getAmount());
        return Response.ok("Bestellung geloescht!").build();    
        } 

        return Response.notModified("not Valid: deleteBestellpunkt").build();
    
    }

    @DELETE
    @Transactional
    @Path("/order")
    @RolesAllowed({"admin", "user"})
    public Response bestellen(@Context SecurityContext securityContext) {
        User user = User.findByName(securityContext.getUserPrincipal().getName());
        Kunde kunde = Kunde.findById(user.kundenid);

        bestellpunktService.Bestellen(kunde);
        return Response.ok("Bestellung abgeschickt!").build();    
    }


    
}
