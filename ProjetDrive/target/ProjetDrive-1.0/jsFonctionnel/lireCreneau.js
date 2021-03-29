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

            var elt2 = document.getElementById("J_userInfo");
            var n1 = location.href.length;
            // location de =
            var n2 = location.href.indexOf("=");
            // pour obtenir idMag
            var idMag = decodeURI(location.href.substr(n2 + 1, n1 - n2));
            elt2.innerHTML = "Magasin " + idMag + " Bonjour " + xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;


            // Elément html que l'on va mettre à jour.
            var creneau = xhr.responseXML.getElementsByTagName("dureeCreneau");
            var nbplace = xhr.responseXML.getElementsByTagName("nbPlaceCreneau");

            var oCar = document.getElementById("car");
            //console.log(creneau.length);
            for (var i = 0; i < creneau.length; i++) {
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                var idCre = i + 1;
                //oDiv.innerHTML += '<div class="check left"> <i class="i_check" id="i_check" onclick="i_check()" >√</i></div>';
                oDiv.innerHTML += '<div class="choisir left" name="1"><input type="radio" id="' + creneau[i].firstChild.nodeValue + '"  name="choisircreneau"  value="' + idCre + '" onclick = "checkClick()"/></div>';
                oDiv.innerHTML += '<div class="heure left"><span id="heure' + idCre + '" name="heure">' + creneau[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="date left"><input id="date' + idCre + '" type="date" name="date" oninput = "checkInput()"></div>';
                oDiv.innerHTML += '<div class="nbplace left"><span>'+nbplace[i].firstChild.nodeValue+'</span></div>';
                oCar.appendChild(oDiv);
            }
            document.getElementById("btnChoisirCre").disabled = "disabled";
        }
    };
    xhr.send();
}
;
function checkClick() {
    var radio = document.getElementsByName("choisircreneau");
    var dateelement = document.getElementsByName("date");
    var date = null;
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            date = dateelement[j].value;
            var d=new Date(Date.parse(date.replace(/-/g,"/")));
            var curDate=new Date();
            if (d >=curDate) {
            document.getElementById("btnChoisirCre").disabled = "";
            }
        }
    }
}
function checkInput() {
    var radio = document.getElementsByName("choisircreneau");
    var dateelement = document.getElementsByName("date");
    var date = null;
    for (j = 0; j < radio.length; j++) {
        date = dateelement[j].value;
        var d=new Date(Date.parse(date.replace(/-/g,"/")));
        var curDate=new Date();
        if(d <curDate){
        alert("Veuillez choisir une date apres aujourd'hui !");
        }
        if(d >=curDate&&radio[j].checked){
            document.getElementById("btnChoisirCre").disabled = "";
        }        
    }
}
//var thetime = document.getElementById("clearDate").value;
//var   d=new   Date(Date.parse(thetime .replace(/-/g,"/")));
//
//var   curDate=new   Date();
//if(d <=curDate){
//alert("请选择大于今天清算时间！");
//}

function choisirCre() {
    var radio = document.getElementsByName("choisircreneau");
    var dateelement = document.getElementsByName("date");
    var heure = 0;
    var idCre = 0;
    var date = null;
    for (j = 0; j < radio.length; j++) {
        if (radio[j].checked) {
            idCre = radio[j].value;
            heure = radio[j].id;
            date = dateelement[j].value;
        }
    }

    alert(heure + "  " + date);
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletStockerCre?creneau=" + heure + "&date=" + date + "&idCre="+idCre);
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
