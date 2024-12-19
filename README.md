# Initialization
## Language utilise
JAVA
## Prérequis
* **Java:** Version 15 ou supérieure.
* **Fichiers JAR:**
  * `jfreechart-1.5.5.jar`
  * `jackson-annotations-2.18.1.jar`
  * `jackson-core-2.18.1.jar`
  * `jackson-databind-2.18.1.jar` 
  * `swing-framework.jar`
  * Placer ces fichiers dans le répertoire `Projet-algav/`.
  * Quand vous importer le projet et tous les fichier jar vous devez les configurer voici les etapes:
  * 1. **Clic droit sur le projet vous cliquez sur build path puis configure build.**
  * 2. **Dans la section "Libraries", Cliquez sur Modulepath puis Add External JARS vous selectionne un jar qui se trouve dans dossier Projet-Algav vous repetez le processus pour tous les autres jars juste pour configurer le path et eviter les erreurs.**

## Conseil pour lancer les commandes bash correctement
### Problème commande bash
Si vous rencontrez l'erreur suivante :
```bash
-bash: ./inserer.sh: cannot execute: required file not found
```
Cela signifie que le fichier inserer.sh n'a pas les permissions d'exécution ou qu'il contient des caractères invisibles (souvent introduits lors d'un transfert depuis Windows).Pour résoudre ce problème :
#### 1-Accorder les permissions d'exécution:
```bash
chmod +x inserer.sh
```
#### 2-Supprimer les caractères de retour chariot:
```bash
sed -i 's/\r$//' inserer.sh
```
### Exécution des classes Experimentation
Si vous obtenez une `StackOverflowError` lors de l'exécution d'une classe dans le package `src/etude`, cela indique probablement une récursion infinie ou une utilisation excessive de la mémoire.Pour augmenter la taille de la pile et éviter cette erreur :
1. **Accédez aux configurations d'exécution de votre IDE.**
2. **Dans la section "Arguments VM", ajoutez l'argument suivant:**

```bash
-Xss10m
```
Cela augmentera la taille de la pile à 10 Mo. Ajustez cette valeur si nécessaire.

