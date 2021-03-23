window.onload = afficherMag();
function afficherMag (){
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletAfficherMagasins");

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){  
           var elt = document.getElementById("lstMagasin");
           
           var tabId = xhr.responseXML.getElementsByTagName("idMag");
           var tabNom = xhr.responseXML.getElementsByTagName("nomMag");
           var tabAdr = xhr.responseXML.getElementsByTagName("adrMag");
           var tabCp = xhr.responseXML.getElementsByTagName("cpMag");
           var tabTel = xhr.responseXML.getElementsByTagName("telMag");
           
           for(var i=0; i<tabId.length; i++){
               var detail = tabNom[i].firstChild.nodeValue+"<br/>"+tabAdr[i].firstChild.nodeValue+"<br/>"+tabCp[i].firstChild.nodeValue+"<br/>"+tabTel[i].firstChild.nodeValue+"<br/>";
               var id = tabId[i].firstChild.nodeValue;
               elt.insertAdjacentHTML("beforeend","<div name='mag' class='magasin'><span id=idMag visibility='hidden'>"+id+"</span>"+detail+"</div><br/>");
           }
        }
    };
	
    // Envoie de la requête.
    xhr.send();
    }
    
function choisirMag(){
    var idMag = this.getElementById("idMag").value;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletChoisirMagasin?idMag="+idMag);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){  
           var elt = document.getElementById("lstMagasin");

           var tabId = xhr.responseXML.getElementsByTagName("idMag");
           var tabDetail = xhr.responseXML.getElementsByTagName("detailMag");

           for(var i=0; i<tabId.length; i++){
               elt.insertAdjacentHTML("beforeend","<div name=mag>"+tabId[i].firstChild.nodeValue+"<br/>"+tabDetail[i].firstChild.nodeValue)+"</div>";
           }
        }
    };
    xhr.send();
    }


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

            /*document.getElementsByName("mag").addEventListener("click",choisirMag);*/
});

