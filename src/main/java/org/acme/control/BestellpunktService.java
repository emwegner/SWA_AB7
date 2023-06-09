package org.acme.control;

import java.util.List;

import org.acme.entity.Bestellungspunkt;
import org.acme.entity.Kunde;
import org.acme.entity.Pizza;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface BestellpunktService {
    public Bestellungspunkt BestellungspunktAbfragen(Long id);
    public List<Bestellungspunkt> BestellungspunkteAbfragen(Kunde kunde);
    public List<Bestellungspunkt> BestellungspunkteAbfragen();
    public void BestellungspunktHinzufuegen(Kunde kunde,Pizza pizza, int amount);
    public void BestellungspunktLoeschen(Kunde kunde,Pizza pizza, int amount);
    public void Bestellen(Kunde kunde);
}
