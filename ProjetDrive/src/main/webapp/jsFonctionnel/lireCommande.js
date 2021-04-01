window.onload = function () {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletLirePanierConfirmer");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200)
        {   // Objet XMLHttpRequest.
            var xhr2 = new XMLHttpRequest();
            // Requête au serveur avec les paramètres éventuels.
            xhr2.open("GET", "ServletAfficherTousSession");
            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr2.onload = function () {
                if (xhr2.status === 200) {
                    var eltMag = document.getElementById("magasin");
                    eltMag.innerHTML = "Magasin : " + xhr2.responseXML.getElementsByTagName("nomMag")[0].firstChild.nodeValue;
                    var eltCre = document.getElementById("heure");
                    eltCre.innerHTML = "Creneau : " + xhr2.responseXML.getElementsByTagName("creneau")[0].firstChild.nodeValue;
                    var eltDate = document.getElementById("date");
                    eltDate.innerHTML = "Date : " + xhr2.responseXML.getElementsByTagName("date")[0].firstChild.nodeValue;
                    var elt2 = document.getElementById("J_userInfo");
                    elt2.innerHTML = "Bonjour " + xhr2.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;
            
                }
            };
            xhr2.send();

            // Elément html que l'on va mettre à jour.
            var image = xhr.responseXML.getElementsByTagName("src");
            var libelleP = xhr.responseXML.getElementsByTagName("libelleP");
            var PrixUnitaireP = xhr.responseXML.getElementsByTagName("PrixUnitaireP");
            var Qte = xhr.responseXML.getElementsByTagName("Qte");
            var idP = xhr.responseXML.getElementsByTagName("idP");
            var promotion = xhr.responseXML.getElementsByTagName("promotion");
            var nouveauprix = xhr.responseXML.getElementsByTagName("nouveauprix");
            var disponibilite = xhr.responseXML.getElementsByTagName("disponibilite");
       
            var oCar = document.getElementById("car");



            for (var i = 0; i < libelleP.length; i++) {
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                oDiv.innerHTML += '<div class="idP left"  id="idpLeftSpan"><span>' + idP[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="img left"><img src="' + image[i].firstChild.nodeValue + '" width="80" height="80"></div>';
                oDiv.innerHTML += '<div class="name left"><span>' + libelleP[i].firstChild.nodeValue + '<br/>' + promotion[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="price left"><span>' + toDecimal2(PrixUnitaireP[i].firstChild.nodeValue) + ' euro</span></div>';
                oDiv.innerHTML += '<div class="prixreel left"><span>'+toDecimal2(nouveauprix[i].firstChild.nodeValue)+' euro</span></div>';
                oDiv.innerHTML += ' <div class="number left"><span>' + Qte[i].firstChild.nodeValue + '</span> </div>';
                oDiv.innerHTML += '<div class="subtotal left"><span>' + toDecimal2(nouveauprix[i].firstChild.nodeValue * Qte[i].firstChild.nodeValue) + ' euro</span></div>';
                var afficherdis = "";     
                if(disponibilite[i].firstChild.nodeValue == 1){
                    afficherdis = "disponible";
                }else if(disponibilite[i].firstChild.nodeValue == 0){
                    afficherdis = "<input type='checkbox' id='"+idP[i].firstChild.nodeValue+"' name='dispon'>";
                }   

                oDiv.innerHTML += '<div class="ctrl left"><span>'+afficherdis+'</span></div>';
                oCar.appendChild(oDiv);
                getAmount();

            }
            var eltdispon = document.getElementsByName("dispon");
            if (eltdispon.length == 0){
                document.getElementById("btnremplacer").style.display = "none";
                document.getElementById("valider").style.display = "block";
            }else if(eltdispon.length > 0){
                document.getElementById("btnremplacer").style.display = "block";
                document.getElementById("valider").style.display = "none";
            }
            document.getElementById("price_eco").innerText = toDecimal2(xhr.responseXML.getElementsByTagName("economie")[0].firstChild.nodeValue);
            document.getElementById("pointspan").innerText = xhr.responseXML.getElementsByTagName("pointfi")[0].firstChild.nodeValue;
        }

    };
    xhr.send();
}

// calculer le prix total
function getAmount() {
    ns = document.getElementsByClassName("name left");
    sum = 0;
    document.getElementById("price_num").innerText = sum;
    for (y = 1; y < ns.length; y++) {
        amount_info = ns[y].parentElement.lastElementChild.previousElementSibling;
        num = parseFloat(amount_info.innerText);
        sum = parseFloat(sum + num);
        document.getElementById("price_num").innerText = sum;
    }
    sum = toDecimal2(sum);
    document.getElementById("price_num").innerText = sum;
}
function toDecimal2(x) { 
    if (isNaN(x)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

function genererCmd() {
    var economie = document.getElementById("price_eco").innerText;
    var xhr = new XMLHttpRequest();    
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletGenererCommande?economie="+economie);
    alert("ServletGenererCommande?economie="+economie);
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {

        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            alert("200");
            alert("La facture est ete envoye. ");
        } else {
            alert("envoye. ");
        }
    };
    xhr.send();
}
function remplacerProd() {
    var eltdispon = document.getElementsByName("dispon");
    var listProdVect = new Array(eltdispon.length);
    for(i = 0;i<eltdispon.length;i++){
        listProdVect[i] = eltdispon[i].id;
    }
    var listProdStrig=listProdVect.toString();
    
//    alert(lidP);
    var xhr = new XMLHttpRequest();    
    // Requete au serveur avec les parametres eventuels.
    xhr.open("GET", "ServletStockerRemplacerProd?lidP="+listProdStrig);
    alert(listProdStrig);
    // On precise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {
        // Si la requete http s'est bien passee.
        if (xhr.status === 200) {
            alert("nice");
        } else {
            alert("pb");
        }
    };
    xhr.send();
}
/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("valider").addEventListener("click", genererCmd);
    document.getElementById("btnremplacer").addEventListener("click", remplacerProd);
});

