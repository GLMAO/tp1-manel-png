[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)
# RSD02
# Objectif du TP
Implémentation du pattern Observer pour créer un système de gestion du temps avec notifications en temps réel, incluant une horloge et des comptes à rebours.
#Étapes de Réalisation

 # Étape 1 : Analyse et Compréhension du Code Existant
Objectif : Prendre en main l'architecture existante
 *Actions réalisées :
 *Analyse des interfaces TimerService, TimerChangeListener, TimeChangeProvider
 *Étude de l'implémentation DummyTimeServiceImpl

Compréhension du mécanisme d'observation
# Étape 2 : Implémentation de la Classe Horloge
*Objectif : Créer une horloge qui affiche l'heure à chaque seconde
 *Implémentation :
 public class Horloge implements TimerChangeListener {
    public Horloge(String name, TimerService timerService) {
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (SECONDE_PROP.equals(evt.getPropertyName())) {
            afficherHeure();
        }
    }
}
# Étape 3 : Injection de Dépendances et Test
 *Objectif : Instancier et tester plusieurs horloges
*Code de test :
TimerService timerService = new DummyTimeServiceImpl();
Horloge horloge1 = new Horloge("Horloge-1", timerService);
Horloge horloge2 = new Horloge("Horloge-2", timerService);

# Étape 4 : Implémentation des Comptes à Rebours
*Objectif : Créer des comptes à rebours qui se désinscrivent automatiquement
*Fonctionnalités :
Décrémentation automatique chaque seconde
Désinscription automatique à 0
Gestion de plusieurs instances

# Étape 5 : Résolution des Problèmes de Concurrence
*Problème identifié : ConcurrentModificationException
*Cause : Modification de la liste des listeners pendant la notification
*Solution : Utilisation de PropertyChangeSupport
*Modification de l'interface :
public interface TimerChangeListener extends PropertyChangeListener {
    // Hérite de propertyChange(PropertyChangeEvent)
}

# Étape 6 : Interface Graphique (Bonus)
*Objectif : Créer une application avec interface utilisateur
*Composants développés :
   HorlogeGraphique : Affichage élégant de l'heure et date
   CompteAReboursGraphique : Comptes à rebours avec barres de progression
   SimpleTimerService : Service de temps simplifié et robuste

# Technologies Utilisées
*Patterns de Conception
*Observer : Notifications des changements de temps
*Dependency Injection : Injection du service de temps
*Separation of Concerns : Séparation interface/implémentation

# API Java Utilisées
*PropertyChangeSupport : Gestion des événements
*Swing : Interface graphique
*Timer et TimerTask : Gestion du temps
*Maven : Gestion des dépendances

# Fonctionnalités Implémentées
*Fonctionnalités de Base
✅ Affichage de l'heure en temps réel sur console
✅ Multiple instances d'horloges
✅ Comptes à rebours avec désinscription automatique
✅ Gestion robuste des événements

*Fonctionnalités Avancées
✅ Interface graphique avec horloge visuelle
✅ Comptes à rebours graphiques avec barres de progression
✅ Changement de couleurs selon le temps restant
✅ Boutons d'arrêt manuel


# Problèmes Rencontrés et Solutions
*Problème 1 : ConcurrentModificationException
Symptôme : Exception lors de la désinscription pendant la notification
Solution : Utilisation de PropertyChangeSupport qui gère la synchronisation

*Problème 2 : Initialisation des Champs Finals
Symptôme : "The blank final field may not have been initialized"
Solution : Initialisation directe dans le constructeur

*Problème 3 : Dépendances entre Modules
Symptôme : Imports non résolus
Solution : Configuration correcte des POMs et création de classes locales  

 # Conclusion
Ce TP a permis de maîtriser le pattern Observer dans un contexte réel. Les principaux apprentissages sont :

Architecture découplée grâce à l'injection de dépendances

Gestion robuste des événements avec PropertyChangeSupport

Interface graphique réactive avec Swing

Résolution de problèmes de concurrence

Gestion de projet multi-modules avec Maven

L'application finale démontre une implémentation professionnelle du pattern Observer, scalable et maintenable.