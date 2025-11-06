package org.emp.gl.clients ; 
import org.emp.gl.timer.service.TimerService ;
import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimeChangeProvider;
import org.emp.gl.timer.service.TimerChangeListener;

public class Horloge implements TimerChangeListener{
 
    String name; 
    TimerService timerService ; 


    public Horloge (String name, TimerService timerService) {
        this.name = name ; 
         this.timerService = timerService;
         // S'inscrire comme listener auprès du TimerService
        this.timerService.addTimeChangeListener(this);
        System.out.println ("Horloge "+name+" initialized!") ;
    }

    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Afficher l'heure à chaque changement de seconde
        if (SECONDE_PROP.equals(prop)) {
            afficherHeure();
        }
        // Vous pouvez aussi gérer les autres types de changement
        else if (MINUTE_PROP.equals(prop)) {
            System.out.println(name + " - Minute changée: " + oldValue + " → " + newValue);
            afficherHeure();
        }
        else if (HEURE_PROP.equals(prop)) {
            System.out.println(name + " - Heure changée: " + oldValue + " → " + newValue);
            afficherHeure();
        }
    }
    
  public void arreter() {
        if (timerService != null) {
            this.timerService.removeTimeChangeListener(this);
        }
    }
  
    public void afficherHeure () {
        if (timerService != null)
            System.out.println (name + " affiche " + 
                                timerService.getHeures() +":"+
                                timerService.getMinutes()+":"+
                                timerService.getSecondes()) ;
    }

}
