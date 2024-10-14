# Architecture
L'application est organisée selon une architecture en couches qui comprend les éléments suivants :

- Controller : Gère les requêtes HTTP et interagit avec le service pour les opérations sur les produits.
- DTO (Data Transfer Object) : Définit les objets utilisés pour transférer les données entre les couches de l'application.
- Entity : Représente les entités de la base de données.
- Enums : Contient les constantes et les types énumérés utilisés dans l'application.
- Exception : Définit les exceptions personnalisées pour gérer les erreurs.
- Mapper : Mappe les objets DTO aux entités et vice versa.
- Service : Contient la logique métier et interagit avec le repository.

# Dépendances
- Spring Boot : Cadre d'application pour construire des applications Java.
- Spring Data JPA : Pour interagir avec la base de données PostgreSQL.
- Jackson : Pour la sérialisation et la désérialisation JSON.
- Lombok : Pour réduire le code boilerplate.
- Swagger : Pour la documentation de l'API.

# Sources de Données
L'application prend en charge deux sources de stockage :

### Fichier JSON : 
- Les produits sont stockés dans un fichier JSON.
### Base de Données PostgreSQL : 
- Les produits peuvent également être stockés dans une base de données PostgreSQL.

# Configuration avant lancement
Merci de bien vouloir configurer les paramètres suivants dans le fichier application.properties avant de lancer l'application.


### Configuration du fichier application.properties
Pour configurer la source des données dans votre projet, modifiez le fichier application.properties comme suit :
```typescript
spring.datasource.url=jdbc:postgresql://localhost:port/nom_de_la_base_de_données
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JSON Storage file path
product.file.path=lien/products.json #(par exemple : C:/Users/Bureau/product-trial-master/back/products.json )
```

#### Note importante
Le fichier JSON utilisé pour stocker les produits doit impérativement être placé en dehors de la solution (par exemple, sur votre bureau ou dans un autre répertoire externe) afin d'assurer que les opérations CRUD sur ce fichier fonctionnent correctement.

#### Cause d'erreur possible
- Si le fichier JSON est placé au sein de la solution (par exemple, dans le dossier src/main/resources) avant la compilation, il sera inclus dans le build final de l'application, souvent en tant que ressource read-only (lecture seule). Cela empêchera toute modification ou mise à jour via les opérations CRUD, entraînant des erreurs lors des tentatives d'écriture ou de suppression des données. Pour éviter cela, il est fortement recommandé de placer le fichier en dehors de la solution.

## Swagger
L'application fournit une API RESTful pour gérer les produits. 
Vous pouvez accéder à la documentation Swagger à l'adresse suivante : http://localhost:8080/swagger-ui/index.html.
