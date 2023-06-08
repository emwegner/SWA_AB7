package org.acme.gateway;

import java.util.List;

import org.acme.control.BestellpunktService;
import org.acme.entity.Bestellungspunkt;
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
    public void BestellungspunktHinzufuegen(Pizza pizza, int amount) {
        Bestellungspunkt bestellungspunkt = new Bestellungspunkt(pizza, amount);
        bestellungspunkt.persist();
    }

    @Override
    public void BestellungspunktLoeschen(Pizza pizza, int amount) {
        List<Bestellungspunkt> punkte = listAll();
        for(Bestellungspunkt punkt : punkte) {
            if(punkt.getPizza().getId()== pizza.getId()) {
                if(punkt.getAmount() == amount) {
                    delete(punkt);
                }
            }
        }
    }

    @Override
    public void Bestellen() {
        deleteAll();
    }
    
}
