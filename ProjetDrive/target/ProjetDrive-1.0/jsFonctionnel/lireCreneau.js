window.onload = afficherCreneau();
function afficherCreneau() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletChoisirCreneau");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            // Objet XMLHttpRequest.
            var xhr2 = new XMLHttpRequest();
            // Requête au serveur avec les paramètres éventuels.
            xhr2.open("GET", "ServletAfficherSession");
            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr2.onload = function () {
                if (xhr2.status === 200) {
                    var elt2 = document.getElementById("J_userInfo");
                    elt2.innerHTML = "Magasin :  " + xhr2.responseXML.getElementsByTagName("nomMag")[0].firstChild.nodeValue+
                            " Bonjour " + xhr2.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;       
                }
            };
            xhr2.send();
            


            // Elément html que l'on va mettre à jour.
            var creneau = xhr.responseXML.getElementsByTagName("dureeCreneau");
            var nbplace = xhr.responseXML.getElementsByTagName("nbPlaceCreneau");
            var idCre = xhr.responseXML.getElementsByTagName("idCre");
            

            var oCar = document.getElementById("car");
            //console.log(creneau.length);
            for (var i = 0; i < creneau.length; i++) {
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                //var idCre = i + 1;
                oDiv.innerHTML += '<div class="choisir left" name="1"><input type="radio" id="' + creneau[i].firstChild.nodeValue + '"  name="choisircreneau"  value="' + idCre[i].firstChild.nodeValue + '" onclick = "checkClick()"/></div>';
                oDiv.innerHTML += '<div class="heure left"><span id="heure' + idCre[i].firstChild.nodeValue + '" name="heure">' + creneau[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="nbplace left"><span>' + nbplace[i].firstChild.nodeValue + '</span></div>';
                oCar.appendChild(oDiv);
            }
            document.getElementById("btnChoisirCre").disabled = "disabled";
        }
    }
    xhr.send();
}
;
function checkClick() {
    var radio = document.getElementsByName("choisircreneau");
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            document.getElementById("btnChoisirCre").disabled = "";
        }
    }
}


function choisirCre() {
    var radio = document.getElementsByName("choisircreneau");
    var heure = 0;
    var idCre = 0;
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            idCre = radio[j].value;
            heure = radio[j].id;
        }
    }

    alert(heure + "  " + idCre);
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletStockerCre?creneau=" + heure + "&idCre=" + idCre);
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            //...
        }
    };
    xhr.send();
}


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("btnChoisirCre").addEventListener("click", choisirCre);
}
);
