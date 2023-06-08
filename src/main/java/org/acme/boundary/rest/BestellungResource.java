package org.acme.boundary.rest;

import java.util.List;

import org.acme.Security.User;
import org.acme.boundary.rest.DTOs.BestellpunktDTO;
import org.acme.control.BestellpunktService;
import org.acme.control.BestellungService;
import org.acme.entity.Bestellungspunkt;
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
    public TemplateInstance getBestellungen() {
        return bestellungTemplate.data("bestellungen",bestellpunktService.BestellungspunkteAbfragen());      
    }

    
    

    @POST
    @Transactional
    @Path("/add")
    //nur user, aber zum testen beide
    @RolesAllowed({"admin", "user"})
    public Response addBestellpunkt(BestellpunktDTO dto) {

      if(dto != null) {
        Pizza pizza = Pizza.findById(dto.getPizzaid());
        bestellpunktService.BestellungspunktHinzufuegen(pizza,dto.getAmount());
        return Response.ok("Bestellung hinzugefuegt!").build();    
        } 
        return Response.notModified("not Valid: addBestellpunkt").build();
    }

    @DELETE
    @Transactional
    @Path("/delete")
    @RolesAllowed({"admin", "user"})
    public Response deleteBestellpunkt(BestellpunktDTO dto) {
      if(dto != null) {
        Pizza pizza = Pizza.findById(dto.getPizzaid());
        bestellpunktService.BestellungspunktLoeschen(pizza,dto.getAmount());
        return Response.ok("Bestellung geloescht!").build();    
        } 

        return Response.notModified("not Valid: addBestellpunkt").build();
    
    }

    @DELETE
    @Transactional
    @Path("/order")
    @RolesAllowed({"admin", "user"})
    public Response bestellen() {
        bestellpunktService.Bestellen();
        return Response.ok("Bestellung abgeschickt!").build();    
    }




    
}
