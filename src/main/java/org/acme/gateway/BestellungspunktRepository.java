package org.acme.gateway;

import java.util.ArrayList;
import java.util.List;

import org.acme.control.BestellpunktService;
import org.acme.entity.Bestellung;
import org.acme.entity.Bestellungspunkt;
import org.acme.entity.Kunde;
import org.acme.entity.Pizza;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class BestellungspunktRepository implements BestellpunktService,PanacheRepository<Bestellungspunkt> {
    
    @Override
    public Bestellungspunkt BestellungspunktAbfragen(Long id) {
       return findById(id);
    }

    @Override
    public List<Bestellungspunkt> BestellungspunkteAbfragen() {
        return listAll();
    }

    @Override
    public void BestellungspunktHinzufuegen(Kunde kunde,Pizza pizza, int amount) {
        Bestellungspunkt bestellungspunkt = new Bestellungspunkt(pizza, amount);
        kunde.addNewBestellpunkt(pizza, amount);
        bestellungspunkt.persist();
        
    }

    @Override
    public void BestellungspunktLoeschen(Kunde kunde,Pizza pizza, int amount) {
        List<Bestellung> bestellungen = kunde.getBestellungen();

        for(Bestellung bestellung:bestellungen) {
            for(Bestellungspunkt bestellungspunkt:bestellung.getPizzen()) {
                if(bestellungspunkt.getPizza().getId()== pizza.getId()) {
                    if(bestellungspunkt.getAmount() == amount) {
                    bestellung.getPizzen().remove(bestellungspunkt);
                      delete(bestellungspunkt); 
                        return;
                    } 
                }
            } 
        
     }

    }

    @Override
    public void Bestellen(Kunde kunde) {
        kunde.order();
    }

    @Override
    public List<Bestellungspunkt> BestellungspunkteAbfragen(Kunde kunde) {
        List<Bestellung> bestellungen = kunde.getBestellungen();
        List<Bestellungspunkt> punkte = new ArrayList<Bestellungspunkt>();
        for(Bestellung bestellung:bestellungen) {
            for(Bestellungspunkt bestellungspunkt:bestellung.getPizzen()) punkte.add(bestellungspunkt);
        }
        return punkte;
    }

    
}

