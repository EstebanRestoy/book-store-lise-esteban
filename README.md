# book-store-lise-esteban

L’objectif est d’implanter sous forme de services le système donné en Figures 1 et Table 1.
La figure décrit le comportement d’un service composite incluant 3 services (ShoppingService,
StockService, WholesalerService) :
- [ ] ShoppingService est appelé par des clients pour connaitre l’état de stock de livres et de
les commander s’il sont disponibles. Ce service appelle le second si et seulement si l’isbn
est valide,
- [ ] StockService retourne la quantité de livres présents en stock,
- [ ] WholesalerService permet de commander des livres au besoin pour augmenter le stock si
la commande est supérieure au stock.
Il vous ai demandé d’implanter cette composition en services REST déployés sur GAE et
Heroku. Le service composite doit pouvoir être appelé via un client Guzzle/silex complet (au
pire Curl). Evidemment, il faut une Bd quelquepart.
Vous devrez me remettre :
- [ ] une, (des) URLs sur les Clouds pour tester + le client configuré,
- [ ] le code du projet au format Eclipse ou Netbeans,
- [ ] un fichier texte me précisant qui a fait quoi, et l’architecture globale du service composite
(ou sont les services, la base, etc.)


## WHAT WE HAVE DONE 

Pour le moment nous avons crée un debut **d'api REST springboot**. l'application est déployé sur Heroku et elle utilise une BDD en postgresql.

## TO DO

- [ ] Dissocier les services (dans heroku et dans GAE)
- [ ] Se mettre d'accord sur les technologies utilisées (BDD / API ).
- [ ] Se mettre d'accord sur les entities utilisées.
- [ ] Se repartir le travail.
