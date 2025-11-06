package org.emp.gl.core.launcher;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleTimerService implements TimerService {
    
    private int secondes = 0;
    private int minutes = 0;
    private int heures = 0;
    private int dixiemes = 0;
    private final PropertyChangeSupport propertyChangeSupport;
    
    public SimpleTimerService() {
        System.out.println("🎯 Construction de SimpleTimerService...");
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        System.out.println("✅ PropertyChangeSupport initialisé");
        startTimer();
        System.out.println("✅ Timer démarré - Service prêt !");
    }
    
    private void startTimer() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateTime();
                } catch (Exception e) {
                    System.err.println("⚠️ Erreur timer: " + e.getMessage());
                }
            }
        }, 100, 100);
    }
    
    private void updateTime() {
        int oldDixiemes = dixiemes;
        dixiemes = (dixiemes + 1) % 10;
        propertyChangeSupport.firePropertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP, oldDixiemes, dixiemes);
        
        if (dixiemes == 0) {
            int oldSecondes = secondes;
            secondes = (secondes + 1) % 60;
            propertyChangeSupport.firePropertyChange(TimerChangeListener.SECONDE_PROP, oldSecondes, secondes);
            
            if (secondes == 0) {
                int oldMinutes = minutes;
                minutes = (minutes + 1) % 60;
                propertyChangeSupport.firePropertyChange(TimerChangeListener.MINUTE_PROP, oldMinutes, minutes);
                
                if (minutes == 0) {
                    int oldHeures = heures;
                    heures = (heures + 1) % 24;
                    propertyChangeSupport.firePropertyChange(TimerChangeListener.HEURE_PROP, oldHeures, heures);
                }
            }
        }
    }

    @Override
    public void addTimeChangeListener(TimerChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public int getDixiemeDeSeconde() { return dixiemes; }
    @Override
    public int getHeures() { return heures; }
    @Override
    public int getMinutes() { return minutes; }
    @Override
    public int getSecondes() { return secondes; }
}