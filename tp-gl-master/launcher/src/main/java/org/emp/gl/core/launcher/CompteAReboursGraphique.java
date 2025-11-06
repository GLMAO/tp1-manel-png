package org.emp.gl.core.launcher;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class CompteAReboursGraphique extends JPanel implements TimerChangeListener {
    
    private final TimerService timerService;
    private final String nom;
    private int compteur;
    private boolean actif;
    
    // Composants graphiques
    private final JLabel labelNom;
    private final JLabel labelCompteur;
    private final JProgressBar progressBar;
    private final JButton boutonArreter;
    
    public CompteAReboursGraphique(String nom, TimerService timerService, int valeurInitiale) {
        this.nom = nom;
        this.timerService = timerService;
        this.compteur = valeurInitiale;
        this.actif = true;
        
        // Initialiser les composants graphiques
        labelNom = new JLabel(nom);
        labelCompteur = new JLabel(String.valueOf(compteur));
        progressBar = new JProgressBar(0, valeurInitiale);
        boutonArreter = new JButton("Arrêter");
        
        // Configurer l'interface
        initialiserInterface();
        
        // S'enregistrer comme listener
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("✅ CompteAReboursGraphique '" + nom + "' créé avec " + compteur + " secondes");
    }
    
    private void initialiserInterface() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
        
        // Configuration des polices et couleurs
        labelNom.setFont(new Font("Arial", Font.BOLD, 14));
        labelNom.setForeground(new Color(70, 70, 70));
        
        labelCompteur.setFont(new Font("Arial", Font.BOLD, 24));
        updateCouleurCompteur();
        
        progressBar.setValue(compteur);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(65, 105, 225)); // RoyalBlue
        
        // Style du bouton
        boutonArreter.setFont(new Font("Arial", Font.PLAIN, 12));
        boutonArreter.setBackground(new Color(220, 20, 60)); // Crimson
        boutonArreter.setForeground(Color.WHITE);
        boutonArreter.setFocusPainted(false);
        
        // Action du bouton
        boutonArreter.addActionListener(e -> arreterManuellement());
        
        // Panel pour le nom et le bouton
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setOpaque(false);
        panelHaut.add(labelNom, BorderLayout.WEST);
        panelHaut.add(boutonArreter, BorderLayout.EAST);
        
        // Panel pour le compteur (centré)
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
        panelCentre.setOpaque(false);
        
        labelCompteur.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentre.add(Box.createVerticalGlue());
        panelCentre.add(labelCompteur);
        panelCentre.add(Box.createVerticalGlue());
        
        // Assemblage des composants
        add(panelHaut, BorderLayout.NORTH);
        add(panelCentre, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        
        // Taille préférée
        setPreferredSize(new Dimension(200, 120));
    }
    
    private void updateCouleurCompteur() {
        if (compteur > 10) {
            labelCompteur.setForeground(new Color(34, 139, 34)); // ForestGreen
        } else if (compteur > 5) {
            labelCompteur.setForeground(new Color(255, 140, 0)); // DarkOrange
        } else {
            labelCompteur.setForeground(new Color(220, 20, 60)); // Crimson
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!actif) return;
        
        // Décrémenter à chaque changement de seconde
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (compteur > 0) {
                compteur--;
                
                // Mettre à jour l'interface dans le thread EDT
                SwingUtilities.invokeLater(() -> {
                    mettreAJourAffichage();
                    
                    // Vérifier si le compte à rebours est terminé
                    if (compteur == 0) {
                        terminerCompteARebours();
                    }
                });
            }
        }
    }
    
    private void mettreAJourAffichage() {
        labelCompteur.setText(String.valueOf(compteur));
        progressBar.setValue(compteur);
        updateCouleurCompteur();
        
        // Animation visuelle pour les dernières secondes
        if (compteur <= 3) {
            setBackground(new Color(255, 250, 205)); // LemonChiffon
        }
    }
    
    private void terminerCompteARebours() {
        actif = false;
        
        // Effets visuels de fin
        setBackground(new Color(255, 215, 0)); // Gold
        labelCompteur.setText("⏰");
        labelCompteur.setForeground(Color.RED);
        progressBar.setValue(0);
        boutonArreter.setEnabled(false);
        
        // Message de fin
        System.out.println("🎉 Compte à rebours '" + nom + "' TERMINÉ !");
        
        // Désinscription automatique
        timerService.removeTimeChangeListener(this);
    }
    
    private void arreterManuellement() {
        if (actif) {
            actif = false;
            setBackground(new Color(211, 211, 211)); // LightGray
            labelCompteur.setText("STOP");
            boutonArreter.setEnabled(false);
            timerService.removeTimeChangeListener(this);
            System.out.println("⏹️ Compte à rebours '" + nom + "' arrêté manuellement");
        }
    }
    
    public void arreter() {
        arreterManuellement();
    }
    
    public int getCompteur() {
        return compteur;
    }
    
    public boolean estActif() {
        return actif;
    }
    
    public String getNom() {
        return nom;
    }
}