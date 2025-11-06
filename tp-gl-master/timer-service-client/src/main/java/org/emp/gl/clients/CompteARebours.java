package org.emp.gl.clients;
import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class CompteARebours implements TimerChangeListener {
    
    private String name;
    private TimerService timerService;
    private int compteur;
    private boolean actif; // Pour gérer l'état actif/inactif

    public CompteARebours(String name, TimerService timerService, int initialValue) {
        this.name = name;
        this.timerService = timerService;
        this.compteur = initialValue;
        this.actif = true;
        
        // S'inscrire comme listener
        this.timerService.addTimeChangeListener(this);
        
        System.out.println(name + " initialisé avec " + compteur + " secondes");
    }

      @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!actif) return;
        
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (compteur > 0) {
                compteur--;
                System.out.println(name + " : " + compteur + " secondes restantes");
                
                if (compteur == 0) {
                    System.out.println("⏰ " + name + " : TERMINÉ !");
                    seDesinscrire();
                }
            }
        }
    }

    /**
     * Méthode pour se désinscrire automatiquement (point 2 de l'énoncé)
     */
    public void seDesinscrire() {
        if (actif && timerService != null) {
            this.timerService.removeTimeChangeListener(this);
            this.actif = false;
            System.out.println("🔴 " + name + " désinscrit du TimerService");
        }
    }

    /**
     * Méthode pour arrêter manuellement si nécessaire
     */
    public void arreter() {
        seDesinscrire();
    }

    public int getCompteur() {
        return compteur;
    }
    
    public boolean estActif() {
        return actif;
    }
}