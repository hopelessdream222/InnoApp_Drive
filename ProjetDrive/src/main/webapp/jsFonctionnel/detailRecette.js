/**
 * Cette méthode affiche les details de la page d'acceuil.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */

window.onload = afficheDetailRecette();
function afficheDetailRecette() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletDetailRecette");

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("ingLib").length; i++) {
                var ing = xhr.responseXML.getElementsByTagName("ingLib")[i].firstChild.nodeValue;
                var qte = xhr.responseXML.getElementsByTagName("qte")[i].firstChild.nodeValue;
                var prodLib = xhr.responseXML.getElementsByTagName("prodLib")[i].firstChild.nodeValue;                
                console.log(ing+"----"+qte+"----"+prodLib);
            }

            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
            }
          
        }
    };

    // Envoie de la requête.
    xhr.send();
}


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    //document.getElementById("btnRechercher").addEventListener("click", rechercher);
});



