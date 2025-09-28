# Démonstration de StampedLock en Java

## Description du projet

Ce projet démontre l'utilisation de `StampedLock`, une fonctionnalité avancée de gestion de concurrence introduite dans Java 8. Il implémente un système simple de gestion de compte bancaire avec des opérations concurrentes de dépôt, retrait et consultation du solde.

## Concepts démontrés

### 1. Gestion de la concurrence avec StampedLock

Le projet illustre trois modes de verrouillage offerts par `StampedLock` :

- **Verrouillage en écriture** : Utilisé pour les opérations de modification (dépôt et retrait) qui nécessitent un accès exclusif.
- **Verrouillage en lecture** : Utilisé pour les lectures standard qui n'interfèrent pas avec d'autres lectures mais bloquent les écritures.
- **Lecture optimiste** : Une approche non-bloquante pour les lectures qui vérifie après l'opération si une modification concurrente a eu lieu.

### 2. Programmation multithreads

Le projet démontre :
- La création et gestion de threads parallèles
- La synchronisation de tâches concurrentes
- La gestion des opérations bloquantes et non-bloquantes
- L'attente de la fin d'exécution des threads avec `join()`

### 3. Bonnes pratiques de gestion des exceptions

Le code illustre :
- La capture et le traitement approprié des `InterruptedException`
- L'utilisation systématique de blocs `try-finally` pour garantir la libération des verrous

## Structure du projet

- `BankAccount.java` : Implémentation d'un compte bancaire utilisant `StampedLock` pour gérer la concurrence
- `StampedLockExampleTest.java` : Classe principale qui démontre l'utilisation concurrente du compte bancaire

## Avantages démontrés de StampedLock par rapport aux autres mécanismes de verrouillage

- **Performance améliorée** : Les lectures optimistes permettent un débit plus élevé dans les scénarios à forte lecture
- **Flexibilité** : Offre trois modes de verrouillage différents selon les besoins (écriture, lecture, optimiste)
- **Évolutivité** : Fonctionne efficacement sous forte charge avec de nombreux threads

## Comment exécuter le projet

Exécutez la classe `StampedLockExampleTest` pour voir les opérations concurrentes en action.