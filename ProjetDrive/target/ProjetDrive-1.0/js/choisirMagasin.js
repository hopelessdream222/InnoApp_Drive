
window.onload=afficherMagasins();

function afficherMagasins (){
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
               elt.insertAdjacentHTML("beforeend",tabId[i].firstChild.values()+"<br/>"+tabDetail[i].firstChild.value());
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

	document.getElementById("btn_image2_voirDetail").addEventListener("click",afficheXML);
	document.getElementById("bt_Url").addEventListener("click",testEncodeUrl);

});
