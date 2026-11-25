Mon application Android permet de rechercher des films de consulter leurs détails et d'ajouter ses propres films dans une base de données locale. 

## Fonctionnalités principales

- **Page d'accueil** : Présentation d'un film mis en avant à la une (affiche grand format, titre, genre, résumé) et liste de films populaires directement accessible en dessous.
- **Recherche de films** : Recherche de films via l'API OMDb avec affichage des résultats sous forme de liste (ou grille selon l'écran) avec affiche, titre et année.
- **Fiche détaillée d'un film** : Affichage des informations complètes du film sélectionné (synopsis, genre, réalisateur, affiche).
- **Affiche en plein écran** : Possibilité de cliquer sur l'affiche d'un film pour l'afficher en grand écran.
- **Ajout de films personnels** : Formulaire permettant d'ajouter manuellement un film (titre, année, réalisateur, genre, synopsis).
- **Stockage local** : Les films ajoutés sont enregistrés sur l'appareil (base de données Room) et apparaissent également dans les résultats de recherche.
- **Interface adaptative** : L'affichage s'adapte à la taille de l'écran (sur grand écran ou en paysage, la liste et les détails s'affichent côte à côte).

## Configuration de la clé API

Pour faire fonctionner la recherche de films :
1. Récupérer une clé sur [omdbapi.com/apikey.aspx](http://www.omdbapi.com/apikey.aspx).
2. Renseigner la clé dans `app/src/main/java/com/example/jeudedes/FilmRepository.kt` :
   ```kotlin
   private val apiKey = "VOTRE_CLE_ICI"
   ```

