package org.acme.control;

import java.util.List;

import org.acme.entity.Bestellungspunkt;
import org.acme.entity.Pizza;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface BestellpunktService {
    public Bestellungspunkt BestellungspunktAbfragen(Long id);
    public List<Bestellungspunkt> BestellungspunkteAbfragen();
    public void BestellungspunktHinzufuegen(Pizza pizza, int amount);
    public void BestellungspunktLoeschen(Pizza pizza, int amount);
    public void Bestellen();
}
