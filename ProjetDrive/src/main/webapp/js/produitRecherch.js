/**
 * Cette méthode affiche les details de la page d'acceuil.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */

window.onload=afficheProds();
function afficheProds (){
         //alert("123");
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET","ServletAffichageProd");

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){  
                alert("200");
                var elt = document.getElementById("corps");                
                if(xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue==="reussi"){
                    alert("zhaodaole!");                    
                    for(var i=0;i<xhr.responseXML.getElementsByTagName("src").length;i++){
                       elt.insertAdjacentHTML("afterbegin","<div class='left'><img src='"
                               +xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue+
                               "' width=200px hight=150px></div><div class='right'>"
                               +xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue+
                               "<input type='button' value='Ajouter' name='Ajouter' id='"
                               +xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue+"'/></div>"); 
                    }
                    var tab = document.getElementsByName("Ajouter");
                    for(var j=0;j<tab.length;j++){
                        tab[j].addEventListener("click",ajouter);
                    }

                }else{
                    alert("meizhaodao!");
                    document.getElementById("zonSaisi").innerHTML="";
                    elt.innerHTML="";
                }               
            }
            if(xhr.responseXML.getElementsByTagName("client")[0]===null){                    
            }else{
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
            }    
        };
        
	// Envoie de la requête.
	xhr.send();
    }

function ajouter() {
    var result = confirm("Vous voulez l'ajouter au panier ?");
    if (result) {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "ServletAjouterPanier?idP=" + event.srcElement.id, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function () {
            // Si la requête http s'est bien passée.
            if (xhr.status === 200) {
                var result2 = alert("Le produit est bien ajoute dans le panier");
            }
        };
    }
    // Envoie de la requête.
    xhr.send();
}

function rechercher (){
         //alert("123");
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
        var nomProd = document.getElementById("zonSaisi").value;
        if(nomProd===""){
            alert("Veuillez saisir un produit");
        }else{
            // Requête au serveur avec les paramètres éventuels.
            xhr.open("GET","ServletRechercheProd?nomProd="+nomProd);
            var elt = document.getElementById("corps");  
            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr.onload = function(){
                // Si la requête http s'est bien passée.
                if (xhr.status === 200){  
                    alert("200");
                    if(xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue==="reussi"){
                        alert("zhaodaole!");
                        window.location.href="ProduitRecherche";
                    }else{
                        alert("meizhaodao!");
                        document.getElementById("zonSaisi").innerHTML="";
                        elt.innerHTML="";
                    }               
                }
            };
        }
	// Envoie de la requête.
	xhr.send();
    }

//function rechercheId(){
//    alert(event.srcElement.id);
//    
//}

/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    
    document.getElementById("btnRechercher").addEventListener("click",rechercher);
    
});



