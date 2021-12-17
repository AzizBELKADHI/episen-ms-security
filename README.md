# Generate Private Key

Je parle ici de certificat auto-généré et par conséquent non-vérifié par une autorité de certification. C'est une approche tout à fait valable pour des tests mais non recommandée en production.

Un fichier .p12 combine à la fois un certificat et une clé privé. Il faut donc commencer par générer ces 2 éléments:


$ openssl req -x509 -newkey rsa:3072 -keyout rsa_private.pem -nodes -out rsa_cert.pem -subj "/CN=unsused"

Cela génère 2 fichiers:

    rsa_private.pem: la clé privée
    rsa_cert.pem: le certificat

Il ne reste plus qu'à combiner les 2 dans un seul ficher .p12 avec la commande suivante:

$ openssl pkcs12 -inkey rsa_private.pem -in rsa_cert.pem -export -out server.p12 -name episen


Une fois le fichier server.p12 générer on peut le vérifier avec la commande:

$ openssl pkcs12 -in server.p12 -noout -info

##### Docker 

Ci-dessous les commandes pour exécuter le fichier Dockerfile.

$ docker build -t episen-ms-security .

$ docker run -d --name episen-ms-security -p 8070:8070 id-image

##### Test Rest API

On commence par lancer une requête de type POST afin de tester l'endpoint 1 qui permet de récupérer la valeur de username et password saisie dans le Body, et par la suite générer le JWT associé à cet utilisateur.

URL : localhost:8088/episen/api/v1/authenticate
Body : {
            "username":"aziz",
            "password":"aziz"
        }
Le résultat de cette requête sera un jeton sous la forme de : "jwt": "[header].[payload].[signature]" 

Passons après à l'endpoint 2 qui permet d'afficher le username d'un utilisateur à partir de son JWT.
Pour faire ce test, on aura besoin de :

    - lancer une requête de type POST avec un URL : localhost:8088/episen/api/v1/verify

    - choisir dans authorization, le type: Bearer Token et dans la valeur de token:[header].[payload].[signature]

Une fois cela est fait, la réponse sera comme suit :
username : aziz