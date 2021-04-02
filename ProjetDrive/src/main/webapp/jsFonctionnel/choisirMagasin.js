window.onload = afficherMag();
function afficherMag() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletAfficherMagasins");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {

            if (xhr.responseXML.getElementsByTagName("client")[0] === null) {
            } else {
                var elt2 = document.getElementById("J_userInfo");
                elt2.innerHTML = "Bonjour " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
            }


            // Elément html que l'on va mettre à jour.
            var elt = document.getElementById("lstMagasin");

            var tabId = xhr.responseXML.getElementsByTagName("idMag");
            var tabNom = xhr.responseXML.getElementsByTagName("nomMag");
            var tabAdr = xhr.responseXML.getElementsByTagName("adrMag");
            var tabCp = xhr.responseXML.getElementsByTagName("cpMag");
            var tabTel = xhr.responseXML.getElementsByTagName("telMag");


            var oCar = document.getElementById("corps");
            //console.log(creneau.length);
            for (var i = 0; i < tabId.length; i++) {
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                oDiv.innerHTML += '<div class="choisir left" name="1"><input type="radio" id="' + tabNom[i].firstChild.nodeValue + '"  name="choisirmagasin"  value="' + tabId[i].firstChild.nodeValue + '" onclick = "checkClick()"/></div>';
                oDiv.innerHTML += '<div class="name left"><span id="nom' + tabId[i].firstChild.nodeValue + '" name="nom">' + tabNom[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="adresse left"><span id="adresse' + tabId[i].firstChild.nodeValue + '" name="adresse">' + tabAdr[i].firstChild.nodeValue + '<br/>' + tabCp[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="tel left"><span id="tel' + tabId[i].firstChild.nodeValue + '" name="tel">' + tabTel[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="date left"><input id="date' + tabId[i].firstChild.nodeValue + '" type="date" name="date" oninput = "checkInput()"></div>';
                oCar.appendChild(oDiv);
            }
            document.getElementById("btnChoisirMag").disabled = "disabled";
        }
    };
    xhr.send();
}
;
function checkClick() {
    var radio = document.getElementsByName("choisirmagasin");
    var dateelement = document.getElementsByName("date");
    var date = null;
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            date = dateelement[j].value;
            var d = new Date(Date.parse(date.replace(/-/g, "/")));
            var curDate = new Date();
            if (d >= curDate) {
                document.getElementById("btnChoisirMag").disabled = "";
            }
        }
    }
}
function checkInput() {
    var radio = document.getElementsByName("choisirmagasin");
    var dateelement = document.getElementsByName("date");
    var date = null;
    for (j = 0; j < radio.length; j++) {
        date = dateelement[j].value;
        var d = new Date(Date.parse(date.replace(/-/g, "/")));
        var curDate = new Date();
        if (d < curDate) {
            alert("Veuillez choisir une date apres aujourd'hui !");
        }
        if (d >= curDate && radio[j].checked) {
            document.getElementById("btnChoisirMag").disabled = "";
        }
    }
}
function choisirMag() {
    var radio = document.getElementsByName("choisirmagasin");
    var dateelement = document.getElementsByName("date");
    var idMag = 0;
    var nomMag = null;
    var date=0;
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            idMag = radio[j].value;
            nomMag = radio[j].id;
            date = dateelement[j].value;
        }
    }
    //location.href="Creneau?"+"magasin="+encodeURI(idMag.value);
    //alert(nomMag + "    " + date+"---------"+idMag);
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletStockerMag?nomMag=" + nomMag + "&date=" + date + "&idMag=" + idMag);

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

    document.getElementById("btnChoisirMag").addEventListener("click", choisirMag);
});

