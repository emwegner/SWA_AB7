package org.acme.Security;

import org.acme.entity.Adresse;
import org.acme.entity.Kunde;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        
        // reset and load all test users
        User.deleteAll();
        User.add("admin", "admin", "admin");
      //  User.add("user", "user", "user");
        Kunde.add("Emil","Mueller", new Adresse("23232","Hausen","Wolfweg","42"));
        Kunde kunde = Kunde.findByLastname("Mueller");
        User.add("user", "user", "user", kunde.getId()) ;
        
    }
}

