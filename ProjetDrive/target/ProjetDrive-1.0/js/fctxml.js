/**
 * Cette méthode "Ajax" affiche le XML.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */
function afficheXML ()
	{
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletDetailProd");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{
			// Elément html que l'on va mettre à jour.
			var elt = document.getElementById("tt_zone");
			elt.innerHTML = xhr.responseText;
			}
		};
	
	// Envoie de la requête.
	xhr.send();
	}


/**
 * Cette méthode "Ajax" affiche la liste des auteurs.
 *
 * Utilise la propriété 'responseXML' de l'objet XMLHttpRequest afin
 * de récupérer sous forme d'arbre DOM le document XML envoyé par le serveur.
 */
function l_auteurs ()
	{
	}


/**
 * Cette méthode "Ajax" affiche la liste des citations.
 *
 * Utilise la propriété 'responseXML' de l'objet XMLHttpRequest afin
 * de récupérer sous forme d'arbre DOM le document XML envoyé par le serveur.
 */
function l_citations ()
	{
	}


/**
 * Cette méthode "Ajax" simule la zone de recherche 'Google'.
 */
function processKey ()
	{
	}


/**
 * Cette méthode "Ajax" permet de tester les paramètres passés par l'url.
 */
function testEncodeUrl ()
	{
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	var param = "texte=" + encodeURIComponent(document.getElementById("envoie").value);
	var url = "ServletEncode";
	alert(url + "?" + param);

	xhr.open("POST",url,true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			// Elément html que l'on va mettre à jour.
			document.getElementById("recue").value = xhr.responseXML.getElementsByTagName("msg")[0].firstChild.nodeValue ;
		};

	// Envoie de la requête.
	xhr.send(param);
	}

window.onload=afficheDetail();
function afficheDetail ()
	{
           //alert("123");
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletDetailProd");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){  
                for(var i=2; i<=5;i++){
                    // Elément html que l'on va mettre à jour.
                    var elt = document.getElementById("image"+i);
                    elt.innerHTML = "<img src='"+xhr.responseXML.getElementsByTagName("src")[i-2].firstChild.nodeValue+"' width=350px hight=250px>";
                    //alert("<img src='"+xhr.responseXML.getElementsByTagName("src")[i-2].firstChild.nodeValue+"'>");
                    //document.getElementById("image"+i+"_prodId").innerHTML=xhr.responseXML.getElementsByTagName("idProd")[i-2].firstChild.nodeValue;
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
