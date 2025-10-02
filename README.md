# Gestion d’un robot (Simulation en Java)

Ce projet est une simulation d’un robot développée en **Java** avec **Eclipse**.  
Il gère le déplacement, la connexion réseau, la livraison de colis, la consommation d’énergie et la maintenance, avec une interface graphique simple.

---

## 📂 Structure du projet
- `src/` : contient les classes Java
  - `Robot.java` : classe de base
  - `RobotConnect.java` : gestion de la connectivité
  - `RobotLivraison.java` : fonctionnalités de livraison
  - `InterfaceRobot.java` : interface graphique utilisateur
  - `MainTestRobot.java` : programme de test
  - Exceptions personnalisées : `RobotException`, `Energie_Insuffisante_Exception`, `Maintenance_Requise_Exception`
  - `connectable.java` : interface de connexion

---

## ⚡ Fonctionnalités
- Connexion / déconnexion du robot
- Envoi de données via une interface connectable
- Déplacement avec gestion d’énergie
- Livraison de colis
- Exceptions pour énergie insuffisante et maintenance requise
- Interface graphique pour interagir avec le robot

---

## 🚀 Lancer le projet
1. Cloner le dépôt :
   ```bash
   git clone https://github.com/zemni31/gestion_d_un_robot.git
