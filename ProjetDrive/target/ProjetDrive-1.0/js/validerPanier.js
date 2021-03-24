/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = validerPanier();    
function validerPanier(){
    alert("text!");
    var xhr = new XMLHttpRequest();
       
        
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletConnexion");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){
                window.location.href="Accueil"
            }
	};
	
	// Envoie de la requête.
	xhr.send();
	
    //Aller à la page pour choisir panier
    //window.location.href="ChoisirMagasin";
    }


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("btnValider").addEventListener("click",validerPanier);
});


