
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
           
            
//            for(var i=2; i<=5;i++){
//                // Elément html que l'on va mettre à jour.
//                var elt = document.getElementById("image"+i);
//                elt.innerHTML = "<img src='"+xhr.responseXML.getElementsByTagName("src")[0].firstChild.nodeValue+"'>";
//                document.getElementById("image"+i+"_prodId").innerHTML=xhr.responseXML.getElementsByTagName("idProd")[0].firstChild.nodeValue;
//            }

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
