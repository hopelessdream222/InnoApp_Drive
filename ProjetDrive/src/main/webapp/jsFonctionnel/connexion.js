/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function seConnecter ()
	{
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
        
        var id = document.getElementById("identifiant").value;
        var mdp = document.getElementById("mdp").value;
        //alert(id);
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletConnexion?id="+id+"&mdp="+mdp);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){
                var response= xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue;
                if(response==="reussi"){
                    alert("bon id ou mdp!");
                    window.location.href="Accueil";
                }else{
                    alert("Mauvais id ou mdp!");
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

	document.getElementById("btnConnexion").addEventListener("click",seConnecter);
	
});


