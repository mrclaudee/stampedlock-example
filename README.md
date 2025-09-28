# StampedLock Banking System

Une implémentation avancée d'un système bancaire multi-threadé utilisant `StampedLock` pour optimiser les performances de lecture avec la lecture optimiste, tout en garantissant la sécurité des transactions concurrentes.

## 🎯 Concepts utilisés

### **StampedLock (Java 8+)**
- **Write Lock** : Verrouillage exclusif pour les opérations de modification (deposit/withdraw)
- **Read Lock** : Verrouillage partagé pour les lectures standard
- **Optimistic Read** : Lecture non-bloquante avec validation pour maximiser les performances

### **Stratégies de verrouillage**
- **Lecture optimiste** : `tryOptimisticRead()` + `validate()` pour éviter le verrouillage quand possible
- **Fallback sécurisé** : Basculement automatique vers read lock si la validation échoue
- **Exclusion mutuelle** : Write locks pour protéger l'intégrité des données

### **Gestion des transactions bancaires**
- **Dépôts thread-safe** : Ajout sécurisé au solde
- **Retraits avec validation** : Vérification du solde avant débit
- **Lecture de solde optimisée** : Performance maximale pour les consultations

### **Architecture concurrente**
- **Multiples threads simultanés** : Dépositaire, retireur, lecteur
- **Synchronisation fine** : Minimisation des contentions entre threads
- **Performance optimisée** : Lectures concurrentes non-bloquantes quand possible

## 🏗️ Architecture

```
BankAccount (Ressource partagée)
├── balance (double) - Solde protégé
├── StampedLock - Mécanisme de verrouillage avancé
├── deposit() - Write lock exclusif
├── withdraw() - Write lock avec validation métier
├── getBalanceOptimistic() - Lecture optimiste + fallback
└── getBalance() - Lecture classique avec read lock

StampedLockExampleTest
├── Depositor Thread - 3 dépôts de 100€
├── Withdrawer Thread - 3 retraits de 50€
├── Reader Thread - 3 lectures optimistes
└── Synchronisation avec join()
```

## 🚀 Installation et exécution

### Prérequis
- Java JDK 8 ou supérieur (StampedLock introduit en Java 8)
- Un terminal

### Compilation
```bash
javac locking/*.java
```

### Exécution

```bash
java locking.StampedLockExampleTest
```

## 📝 Comportement observé

### Exemple de sortie typique

```
Depositor depositing: 100.0
Depositor new balance: 100.0
Reader reading balance: 100.0
Withdrawer withdrew: 50.0, New balance: 50.0
Depositor depositing: 100.0
Depositor new balance: 150.0
Reader reading balance: 150.0
Withdrawer withdrew: 50.0, New balance: 100.0
Depositor depositing: 100.0
Depositor new balance: 200.0
Withdrawer withdrew: 50.0, New balance: 150.0
Reader reading balance: 150.0
Final Balance: 150.0
```

### Scénarios de verrouillage

**Lecture optimiste réussie :**
- `tryOptimisticRead()` obtient un stamp
- Lecture du solde sans blocage
- `validate()` confirme - pas de write concurrent

**Lecture optimiste échouée :**
- `tryOptimisticRead()` obtient un stamp
- Write concurrent invalide le stamp
- Fallback automatique vers `readLock()`

## 🔧 Fonctionnalités

- **Dépôts sécurisés** : Ajout thread-safe au solde avec write lock
- **Retraits validés** : Vérification du solde suffisant avant débit
- **Lectures optimisées** : Performance maximale avec lecture optimiste
- **Fallback automatique** : Basculement transparent vers read lock si nécessaire
- **Transactions atomiques** : Garantie de cohérence des données

## 📚 Points d'apprentissage

Ce projet illustre parfaitement :
- **L'utilisation avancée de StampedLock** vs ReentrantReadWriteLock
- **L'optimisation des performances de lecture** avec la lecture optimiste
- **La gestion des contentions** dans un système concurrent
- **Le pattern try-finally** pour la libération des verrous
- **La validation d'intégrité** avec `validate()`
- **Les transactions bancaires thread-safe**

## ⚡ Avantages de StampedLock

### Par rapport à synchronized :
- **Meilleure scalabilité** avec multiple readers
- **Lectures non-bloquantes** avec optimistic read
- **Performance supérieure** sous forte charge

### Par rapport à ReentrantReadWriteLock :
- **Lecture optimiste** évite complètement le verrouillage
- **Moins de overhead** pour les lectures fréquentes
- **Meilleur throughput** dans les scénarios read-heavy

## 🎛️ Configuration

Dans `StampedLockExampleTest.java` :
```java
Thread.sleep(500L); // Délai entre opérations
account.deposit(100); // Montant des dépôts
account.withdraw(50); // Montant des retraits
```

## 🚀 Extensions possibles

- Ajouter des métriques de performance (temps de lock, contentions)
- Implémenter un historique des transactions
- Ajouter la gestion des découverts autorisés
- Créer plusieurs comptes avec transferts inter-comptes
- Implémenter un système de notification d'événements
- Ajouter des tests de charge avec plus de threads
- Intégrer des timeouts sur les opérations de verrouillage

## ⚠️ Notes importantes

- **StampedLock n'est PAS réentrant** (contrairement à ReentrantReadWriteLock)
- **Attention aux deadlocks** si mal utilisé
- **Toujours libérer les verrous** dans un bloc finally
- **Valider les stamps** pour la lecture optimiste
