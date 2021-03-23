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
           var tabDetail = xhr.responseXML.getElementsByTagName("detailMag");

           for(var i=0; i<tabId.length; i++){
               elt.insertAdjacentHTML("beforeend","<div name=mag ><span id=idMag>"+tabId[i].firstChild.values()+"</span>"+tabDetail[i].firstChild.value())+"</div>";
           }
        }
    };
	
    // Envoie de la requête.
    xhr.send();
    }
    
function choisirMag(){
    var idMag = this.
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


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

	document.getElementsByName("mag").addEventListener("click",choisirMag);
});
