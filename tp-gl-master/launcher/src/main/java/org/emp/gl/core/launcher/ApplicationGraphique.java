package org.emp.gl.core.launcher;

import org.emp.gl.timer.service.TimerService;
import javax.swing.*;

public class ApplicationGraphique {
    
    public static void main(String[] args) {
        System.out.println("🚀 DÉMARRAGE APPLICATION HORLOGE GRAPHIQUE");
        System.out.println("===========================================");
        
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("🔧 Création du TimerService...");
                TimerService timerService = new SimpleTimerService();
                System.out.println("✅ TimerService créé");
                
                System.out.println("🔧 Création de l'HorlogeGraphique...");
                new HorlogeGraphique(timerService);
                System.out.println("✅ HorlogeGraphique créée");
                
                System.out.println("🎉 APPLICATION DÉMARRÉE AVEC SUCCÈS !");
                System.out.println("👉 L'horloge graphique devrait s'afficher...");
                
            } catch (Exception e) {
                System.err.println("💥 ERREUR CRITIQUE: " + e.getMessage());
                e.printStackTrace();
                showErrorDialog("Impossible de démarrer l'application: " + e.getMessage());
            }
        });
    }
    
    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null,
            message + "\n\nConsultez la console pour les détails techniques.",
            "Erreur de Démarrage",
            JOptionPane.ERROR_MESSAGE);
    }
}