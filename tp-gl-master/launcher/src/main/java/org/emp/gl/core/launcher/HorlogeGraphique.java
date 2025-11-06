package org.emp.gl.core.launcher;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class HorlogeGraphique extends JFrame implements TimerChangeListener {
    
    private final TimerService timerService;
    private final JLabel timeLabel;
    private final JLabel dateLabel;
    
    public HorlogeGraphique(TimerService timerService) {
        this.timerService = timerService;
        
        // ✅ INITIALISATION DES CHAMPS FINALS
        this.timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        this.dateLabel = new JLabel("Chargement...", SwingConstants.CENTER);
        
        // Configuration de la fenêtre
        setTitle("🕐 Horloge Graphique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Initialiser l'interface
        initializeUI();
        
        // S'enregistrer comme listener
        this.timerService.addTimeChangeListener(this);
        
        // Afficher l'heure initiale
        updateDisplay();
        
        // Afficher la fenêtre
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        System.out.println("✅ HorlogeGraphique créée avec succès !");
    }
    
    private void initializeUI() {
        // Panel principal avec fond dégradé
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(240, 248, 255); // AliceBlue
                Color color2 = new Color(230, 230, 250); // Lavender
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Configuration du label d'heure
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setForeground(new Color(25, 25, 112)); // MidnightBlue
        timeLabel.setOpaque(false);
        
        // Configuration du label de date
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dateLabel.setForeground(new Color(105, 105, 105)); // DimGray
        dateLabel.setOpaque(false);
        
        // Panel pour centrer le contenu
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(timeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(dateLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Mettre à jour l'interface à chaque changement de temps
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName()) ||
            TimerChangeListener.MINUTE_PROP.equals(evt.getPropertyName()) ||
            TimerChangeListener.HEURE_PROP.equals(evt.getPropertyName())) {
            
            updateDisplay();
        }
    }
    
    private void updateDisplay() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Mettre à jour l'heure
                String time = String.format("%02d:%02d:%02d",
                    timerService.getHeures(),
                    timerService.getMinutes(),
                    timerService.getSecondes());
                timeLabel.setText(time);
                
                // Mettre à jour la date
                java.text.SimpleDateFormat dateFormat = 
                    new java.text.SimpleDateFormat("EEEE d MMMM yyyy", java.util.Locale.FRENCH);
                String date = dateFormat.format(new java.util.Date());
                dateLabel.setText(date);
                
            } catch (Exception e) {
                System.err.println("❌ Erreur mise à jour affichage: " + e.getMessage());
                timeLabel.setText("ERREUR");
                timeLabel.setForeground(Color.RED);
            }
        });
    }
    
    public void fermer() {
        if (timerService != null) {
            timerService.removeTimeChangeListener(this);
        }
        dispose();
        System.out.println("🔴 HorlogeGraphique fermée");
    }
}