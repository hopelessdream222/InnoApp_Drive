/**
 * Cette m�thode affiche les details de la page d'acceuil.
 *
 * On utilise la propri�t� 'responseText' de l'objet XMLHttpRequest afin
 * de r�cup�rer sous forme de texte le flux envoy� par le serveur.
 */

window.onload = afficheDetailRecette();
function afficheDetailRecette() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ�te au serveur avec les param�tres �ventuels.
    xhr.open("GET", "ServletDetailRecette");

    // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
    xhr.onload = function () {
        // Si la requ�te http s'est bien pass�e.
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

    // Envoie de la requ�te.
    xhr.send();
}


/**
 * Lancement apr�s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    //document.getElementById("btnRechercher").addEventListener("click", rechercher);
});



