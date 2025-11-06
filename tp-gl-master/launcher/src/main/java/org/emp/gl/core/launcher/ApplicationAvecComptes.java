package org.emp.gl.core.launcher;

import org.emp.gl.timer.service.TimerService;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ApplicationAvecComptes {
    
    public static void main(String[] args) {
        System.out.println("🚀 DÉMARRAGE APPLICATION AVEC COMPTES À REBOURS");
        System.out.println("================================================");
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer le service de temps
                TimerService timerService = new SimpleTimerService();
                
                // Créer l'horloge graphique
                new HorlogeGraphique(timerService);
                
                // Créer la fenêtre des comptes à rebours
                creerFenetreComptes(timerService);
                
                System.out.println("🎉 APPLICATION COMPLÈTE DÉMARRÉE !");
                
            } catch (Exception e) {
                System.err.println("💥 ERREUR: " + e.getMessage());
                e.printStackTrace();
                showErrorDialog(e);
            }
        });
    }
    
    private static void creerFenetreComptes(TimerService timerService) {
        JFrame frame = new JFrame("⏱️ Comptes à Rebours");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Titre
        JLabel titre = new JLabel("10 Comptes à Rebours Aléatoires", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(70, 70, 70));
        titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(titre, BorderLayout.NORTH);
        
        // Panel pour les comptes à rebours (grille 5x2)
        JPanel panelComptes = new JPanel(new GridLayout(5, 2, 10, 10));
        panelComptes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelComptes.setBackground(new Color(240, 240, 240));
        
        // Créer 10 comptes à rebours avec valeurs aléatoires entre 10 et 20
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int valeurInitiale = 10 + random.nextInt(11); // 10 à 20 secondes
            CompteAReboursGraphique compte = 
                new CompteAReboursGraphique("Compte-" + (i+1), timerService, valeurInitiale);
            panelComptes.add(compte);
        }
        
        // Panel scrollable
        JScrollPane scrollPane = new JScrollPane(panelComptes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de statut
        JLabel statut = new JLabel("Les comptes à rebours se terminent automatiquement à 0", 
                                  SwingConstants.CENTER);
        statut.setForeground(Color.GRAY);
        statut.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        frame.add(statut, BorderLayout.SOUTH);
        
        // Configuration de la fenêtre
        frame.pack();
        frame.setSize(900, 700);
        frame.setLocation(100, 100);
        frame.setVisible(true);
        
        System.out.println("✅ Fenêtre des comptes à rebours créée");
    }
    
    private static void showErrorDialog(Exception e) {
        JOptionPane.showMessageDialog(null,
            "Erreur: " + e.getMessage(),
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
    }
}