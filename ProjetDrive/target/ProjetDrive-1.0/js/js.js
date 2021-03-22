/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function afficheXM ()
	{
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletProduit");
	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
			var elt = document.getElementById("tt_zone");
                        var res = xhr.responseXML.getElementsByTagName("libelleP");
                        var text="";			
                        for(var i=0;i<res.length;i++){
                            text+="<p>"+res[i].firstChild.nodeValue+"</p>";
                        }
			elt.innerHTML = xhr.responseText;
			}
		};
	
	// Envoie de la requête.
	xhr.send();
	}
    document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("bt_zone").addEventListener("click",afficheXML);
    });

